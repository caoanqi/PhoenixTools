package com.phoenix.ulin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phoenix.ulin.R;

import java.util.List;

/**
 * 功能适配器
 *
 * @author caoyl
 * @since 2020-01-05
 */
public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.FunctionViewHolder> {
    private Context context;
    private List<String> funcs;
    private FuncCallback funcCallback;

    public FunctionAdapter(Context ctx) {
        context = ctx;
    }

    public FunctionAdapter(Context ctx, List<String> funcs, FuncCallback funcCallback) {
        context = ctx;
        this.funcs = funcs;
        this.funcCallback = funcCallback;
    }

    @NonNull
    @Override
    public FunctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_function, parent, false);
        FunctionViewHolder viewHolder = new FunctionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionViewHolder holder, int position) {
        holder.tvFuncName.setText(funcs.get(position));
    }

    @Override
    public int getItemCount() {
        return funcs.size();
    }

    public class FunctionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFuncName;

        public FunctionViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            initListener();
        }

        private void initView(View view) {
            tvFuncName = view.findViewById(R.id.tv_func_name);
        }

        private void initListener() {
            tvFuncName.setOnClickListener(v -> {
                if (funcCallback != null) {
                    funcCallback.onFuncItemClick(funcs.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface FuncCallback {
        void onFuncItemClick(String funcName);
    }
}
