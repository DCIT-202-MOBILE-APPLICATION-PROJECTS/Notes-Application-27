package notes.digerati.scribble.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>  {
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }


}

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
