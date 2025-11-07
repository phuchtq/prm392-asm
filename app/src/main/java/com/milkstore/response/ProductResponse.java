package com.milkstore.response;

import com.milkstore.models.Product;

import java.util.List;



    public class ProductResponse {
        private List<Product> content;

        public List<Product> getContent() {
            return content;
        }

        public void setContent(List<Product> content) {
            this.content = content;
        }
    }

