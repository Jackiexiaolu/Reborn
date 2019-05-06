package com.demo.reborn.financialintelligence;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

public interface FinancialIntelligenceContract {
    interface View extends BaseView<Presenter>{
        void setInsuranceDropItems(String[] insurance);
        void setMainArticleDropItems(String[] mainArticle);
        void setProductDropItems(String[] product);
        void clickJump();
    }
    interface Presenter extends BasePresenter{
        void setInsurance();
        void setMainArticle();
        void setProduct();
        void finalSubmit();
    }
}
