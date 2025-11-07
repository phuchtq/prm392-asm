package com.milkstoremobile_fronend.response;

import com.milkstoremobile_fronend.models.Product;

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

