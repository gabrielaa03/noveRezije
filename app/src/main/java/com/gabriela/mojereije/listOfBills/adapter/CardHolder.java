package com.gabriela.mojereije.listOfBills.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.gabriela.mojereije.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public CardHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
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