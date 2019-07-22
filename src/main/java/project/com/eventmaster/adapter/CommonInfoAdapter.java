package project.com.eventmaster.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import project.com.eventmaster.data.CommonInfo;

public class CommonInfoAdapter extends RecyclerView.Adapter<CommonInfoViewHolder> {

    List<CommonInfo> infos;

    public CommonInfoAdapter(List<CommonInfo> infos) {
        this.infos = infos;
    }

    public void setInfos(List<CommonInfo> infos) {
        this.infos = infos;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommonInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return CommonInfoViewHolder.create(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonInfoViewHolder commonInfoViewHolder, int i) {
        CommonInfo info = infos.get(i);
        commonInfoViewHolder.bind(info.getLabel(), info.getValue() );
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }
}
