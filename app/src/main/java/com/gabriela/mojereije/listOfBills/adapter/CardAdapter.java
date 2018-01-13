package com.gabriela.mojereije.listOfBills.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.model.data_models.Bill;
import com.gabriela.mojereije.utils.WidgetUtils;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardHolder> {

    private List<Bill> billList = new ArrayList<>();
    private String paidType;

    public CardAdapter(String paidType) {
        this.paidType = paidType;
    }

    public void setBillList(List<Bill> billList) {
        this.billList.clear();
        this.billList.addAll(billList);
        notifyDataSetChanged();
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(paidType.equals("paid")) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout_green, parent, false);
            return new CardHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout_red, parent, false);
            return new CardHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        Bill bill = billList.get(position);
        holder.setData(bill.getTvrtka(), bill.getIznos() + " kn", bill.getNaziv(), bill.getBrojRacuna(), bill.getMjesec() + "    (god/mj)", WidgetUtils.setPaidState(bill.getStanje()));
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }
}