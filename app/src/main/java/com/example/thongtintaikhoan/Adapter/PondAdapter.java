package com.example.thongtintaikhoan.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PondAdapter extends RecyclerView.Adapter<PondAdapter.UserViewHolder>{
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
//
//    private List<Pond> mListUser;
//    private Context mContext;
////    private Context mContext;
//
//    public PondAdapter(Context context ,List<Pond> mListUser) {
//
////        this.mContext =  context;
//        this.mListUser = mListUser;
//        this.mContext = context;
//    }
//
//    @NonNull
//    @Override
//    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fishpond, parent, false);
//        return new UserViewHolder(view);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
//        final Pond pond =mListUser.get(position);
//        if(pond == null){
//            return;
//        }
//
//        holder.tvName.setText(pond.getName());
//        holder.tvInfoPond.setText(pond.getPond_area());
//
//        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                onClickGoToDetail(pond);
//
//            }
//        });
//    }
////
////    private void onClickGoToDetail(Pond pond){
////        Intent intent = new Intent( mContext, DetailActivity.class);
////        Bundle bundle = new Bundle();
////        bundle.putSerializable("object_user", user);
////        intent.putExtras(bundle);
////        mContext.startActivity(intent);
////    }
//
////    public void release(){
////        mContext = null;
////    }
//
//    @Override
//    public int getItemCount() {
//        if(mListUser != null){
//            return mListUser.size();
//        }
//
//        return 0;
//    }
//
//    public class UserViewHolder extends RecyclerView.ViewHolder{
//
//        private RelativeLayout layoutItem;
//
//
//        private TextView tvName;
//        private TextView tvInfoPond;
//
//        public UserViewHolder(@NonNull View itemView) {
//            super(itemView);
//            layoutItem= itemView.findViewById(R.id.layout_item1);
//
//            tvName = itemView.findViewById(R.id.tv_namepond1);
//            tvInfoPond = itemView.findViewById(R.id.tv_description);
//        }
//    }
}
