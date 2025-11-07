package com.milkstoremobile_fronend.views.adapters.Messages;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.milkstoremobile_fronend.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_USER = 1;
    private static final int TYPE_AI = 2;

    private List<String> messages;

    public MessageAdapter(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        String msg = messages.get(position);
        if (msg.startsWith("Bạn:")) {
            return TYPE_USER;
        } else {
            return TYPE_AI;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_USER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new UserMessageHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new AiMessageHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String msg = messages.get(position);

        if (holder instanceof UserMessageHolder) {
            ((UserMessageHolder) holder).tvMessage.setText(msg.replaceFirst("Bạn: ", ""));
        } else if (holder instanceof AiMessageHolder) {
            String aiText = msg.replaceFirst("AI: ", "")
                    .replace("\n", "<br>")                    // xuống dòng
                    .replace("* ", "• ")                      // chuyển dấu * thành bullet
                    .replace("**", "");                       // bỏ định dạng đậm nếu không xử lý markdown

            Spanned formatted = Html.fromHtml(aiText, Html.FROM_HTML_MODE_LEGACY);
            ((AiMessageHolder) holder).tvMessage.setText(formatted);
        }
    }



    @Override
    public int getItemCount() {
        return messages != null ? messages.size() : 0;
    }

    static class UserMessageHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;

        public UserMessageHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }

    static class AiMessageHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;

        public AiMessageHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
