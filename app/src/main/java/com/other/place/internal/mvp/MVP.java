package com.other.place.internal.mvp;

/**
 * Created by thinh.vo on 17/11/2016.
 */

public interface MVP {
    interface Model{
        interface OnLoad<T>{
            void onSuccess(T data);
            void onFail(String error);
        }
    }
    interface View{
        void showLoading();
        void showError(String error);
        void showContent();
        void showEmpty();
    }
}
