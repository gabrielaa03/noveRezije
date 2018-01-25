package com.gabriela.mojereije.listOfBills.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.listOfBills.ListOfBills;
import com.gabriela.mojereije.model.data_models.Bill;
import com.gabriela.mojereije.utils.RealmUtils;
import com.gabriela.mojereije.utils.SharedPrefs;
import com.gabriela.mojereije.utils.WidgetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private List<Bill> billList = new ArrayList<>();
    private String paidType;
    private Context context;

    public CardAdapter(String paidType,Context context) {
        this.paidType = paidType;
        this.context = context;
    }

    public void setBillList(List<Bill> billList) {
        this.billList.clear();
        this.billList.addAll(billList);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        billList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        notifyDataSetChanged();
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardHolder holder, final int position) {
        final Bill bill = billList.get(position);
        final String brojRacuna = bill.getBrojRacuna();
        holder.setBill(bill);
        holder.setData(bill.getTvrtka(), bill.getIznos() + " kn", bill.getNaziv(), bill.getBrojRacuna(), bill.getMjesec() + "    (god/mj)", WidgetUtils.setPaidState(bill.getStanje()));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmUtils.deleteItem(brojRacuna, SharedPrefs.getSharedPrefs("username", holder.itemView.getContext()));
                removeItem(position);
                WidgetUtils.setToast(holder.itemView.getContext(), R.string.billDeleted);
            }
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_tvrtkaUpis)
        TextView companyName;

        @BindView(R.id.iznos)
        TextView priceToPay;

        @BindView(R.id.tv_descUpis)
        TextView description;

        @BindView(R.id.tv_mjesecUpis)
        TextView month;

        @BindView(R.id.tv_brojRacunaUpis)
        TextView billID;

        @BindView(R.id.tv_stanjeUpis)
        TextView isPaid;

        @BindView(R.id.deleteButton)
        ImageButton deleteButton;

        private Bill bill;
        View view;

        public CardHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (!paidType.equals("paid")) {
                isPaid.setTextColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                description.setTextColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                priceToPay.setBackgroundColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
                priceToPay.setBackground(itemView.getResources().getDrawable(R.drawable.button_shape_red2));
            }
            view = itemView;
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String brojRacuna = bill.getBrojRacuna();
                    RealmUtils.changeBill(brojRacuna, SharedPrefs.getSharedPrefs("username",  itemView.getContext()));
                    if(context instanceof ListOfBills){
                        ((ListOfBills) context).refreshRecyclerViews();
                    }
                    return true;
                }
            });
        }

        public void setBill(Bill bill) {
            this.bill = bill;
        }

        public void setData(String companyName, String priceToPay, String description, String billID, String month, String stanje) {
            this.companyName.setText(companyName);
            this.priceToPay.setText(priceToPay);
            this.description.setText(description);
            this.billID.setText(billID);
            this.month.setText(month);
            this.isPaid.setText(stanje);
        }
    }
}