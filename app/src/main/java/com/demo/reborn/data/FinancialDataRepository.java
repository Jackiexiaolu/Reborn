package com.demo.reborn.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.reborn.data.json.Api1_BaseInfo;
import com.demo.reborn.data.json.Api1_Business;
import com.demo.reborn.data.json.Api1_Business_Opportunity;
import com.demo.reborn.data.json.Api1_ChangeInfo;
import com.demo.reborn.data.json.Api1_CollectionList;
import com.demo.reborn.data.json.Api1_CompanyList;
import com.demo.reborn.data.json.Api1_Dic_Filling;
import com.demo.reborn.data.json.Api1_FinancialStatement;
import com.demo.reborn.data.json.Api1_FinancingGroupInfo;
import com.demo.reborn.data.json.Api1_FinancingGroupInfo0729;
import com.demo.reborn.data.json.Api1_FinancingIndex;
import com.demo.reborn.data.json.Api1_FinancingInfo;
import com.demo.reborn.data.json.Api1_FinancingInfo0729;
import com.demo.reborn.data.json.Api1_FirmgraphHolders;
import com.demo.reborn.data.json.Api1_FirmgraphHolders_single;
import com.demo.reborn.data.json.Api1_FirmgraphInvestments;
import com.demo.reborn.data.json.Api1_FriendsList;
import com.demo.reborn.data.json.Api1_Holders;
import com.demo.reborn.data.json.Api1_IndustryAverage;
import com.demo.reborn.data.json.Api1_IndustryAvgList;
import com.demo.reborn.data.json.Api1_Managers;
import com.demo.reborn.data.json.Api1_NewFirmgraphHolders;
import com.demo.reborn.data.json.Api1_Opportunity_Abstract;
import com.demo.reborn.data.json.Api1_Receive_Friend;
import com.demo.reborn.data.json.Api1_Receive_Friends_Message;
import com.demo.reborn.data.json.Api1_SearchCompany;
import com.demo.reborn.data.json.Api1_SearchIndustry;
import com.demo.reborn.data.json.Api1_SearchInstitution;
import com.demo.reborn.data.json.Api1_Search_Users;
import com.demo.reborn.data.json.Api1_Send_Friend_Response;
import com.demo.reborn.data.json.Api1_Send_Friends_Message;
import com.demo.reborn.data.json.Api1_ShowUserInfo;
import com.demo.reborn.data.json.Api1_Test;
import com.demo.reborn.data.json.Api1_post_common;
import com.demo.reborn.data.json.Api1_post_temporary_trust;
import com.demo.reborn.data.json.Api1_post_token;
import com.demo.reborn.data.local.FinancialDataLocal;
import com.demo.reborn.data.remote.FinancialDataRemote;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.demo.reborn.util.MyApplication.getContext;

public class FinancialDataRepository implements FinancialDataInterface {


    public static FinancialDataRepository INSTANCE = null;

    public static FinancialDataRepository getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new FinancialDataRepository();
        return INSTANCE;
    }

    private FinancialDataLocal mLocal;
    private FinancialDataRemote mRemote;

    public FinancialDataRepository() {
        mLocal = new FinancialDataLocal();
        mRemote = new FinancialDataRemote();
    }

    @Override
    public Observable<Response<Api1_FirmgraphHolders_single>> get_Api1_FirmgraphHolders_single(String c_id) {
        Observable<Response<Api1_FirmgraphHolders_single>> res = mLocal.get_Api1_FirmgraphHolders_single(c_id);
        if (res != null)
            return res;
        return mRemote.get_Api1_FirmgraphHolders_single(c_id);
    }

    @Override
    public Observable<Response<Api1_FinancingIndex>> get_Api1_FinancingIndex(String c_id) {
        Observable<Response<Api1_FinancingIndex>> res = mLocal.get_Api1_FinancingIndex(c_id);
        if (res != null)
            return res;
        return mRemote.get_Api1_FinancingIndex(c_id);
    }

    @Override
    public Observable<Api1_Test> get_Api1_Test(int gg) {
        Observable<Api1_Test> res = mLocal.get_Api1_Test(gg);
        if (res != null)
            return res;
        return mRemote.get_Api1_Test(gg);
    }

    @Override
    public Observable<Response<Api1_CompanyList>> get_Api1_CompanyList(int limit, int page) {
        Observable<Response<Api1_CompanyList>> res = mLocal.get_Api1_CompanyList(limit, page);
        if (res != null)
            return res;
        return mRemote.get_Api1_CompanyList(limit, page);
    }

    @Override
    public Observable<Response<Api1_SearchCompany>> get_Api1_SearchCompany(String keyword, String industry, int limit, int page) {
        return mRemote.get_Api1_SearchCompany(keyword, industry, limit, page);
    }

    @Override
    public Observable<Response<Api1_SearchIndustry>> get_Api1_SearchIndustry(String industry, int limit, int page) {
        return mRemote.get_Api1_SearchIndustry(industry, limit, page);
    }

    @Override
    public Observable<Response<Api1_BaseInfo>> get_Api1_BaseInfo(String c_id) {
        return mRemote.get_Api1_BaseInfo(c_id);
    }

    @Override
    public Observable<Response<Api1_Holders>> get_Api1_Holders(String c_id) {
        return mRemote.get_Api1_Holders(c_id);
    }

    @Override
    public Observable<Api1_FirmgraphHolders> get_Api1_FirmgraphHolders(String c_id, String name) {
        return mRemote.get_Api1_FirmgraphHolders(c_id, name);
    }

    @Override
    public Observable<Response<Api1_FirmgraphInvestments>> get_Api1_FirmgraphInvestments(String c_id) {
        return mRemote.get_Api1_FirmgraphInvestments(c_id);
    }

    @Override
    public Observable<Response<Api1_Managers>> get_Api1_Managers(String c_id) {
        return mRemote.get_Api1_Managers(c_id);
    }

    @Override
    public Observable<Response<Api1_ChangeInfo>> get_Api1_ChangeInfo(String c_id) {
        return mRemote.get_Api1_ChangeInfo(c_id);
    }

    @Override
    public Observable<Response<Api1_Business>> get_Api1_Business(String c_id) {
        return mRemote.get_Api1_Business(c_id);
    }

    @Override
    public Observable<Response<Api1_FinancialStatement>> get_Api1_FinancialStatement(String c_id,String time) {
        return mRemote.get_Api1_FinancialStatement(c_id,time);
    }

    @Override
    public Observable<Api1_FinancingInfo> get_Api1_FinancingInfo(String c_id) {
        return mRemote.get_Api1_FinancingInfo(c_id);
    }

    @Override
    public Observable<Api1_FinancingGroupInfo> get_Api1_FinancingGroupInfo(String g_id) {
        return mRemote.get_Api1_FinancingGroupInfo(g_id);
    }

    @Override
    public Observable<Response<Api1_FinancingInfo0729>> get_Api1_FinancingInfo0729(String c_id) {
        return mRemote.get_Api1_FinancingInfo0729(c_id);
    }

    @Override
    public Observable<Response<Api1_FinancingGroupInfo0729>> get_Api1_FinancingGroupInfo0729(String g_id,String g_name) {
        return mRemote.get_Api1_FinancingGroupInfo0729(g_id,g_name);
    }

    @Override
    public Observable<Response<Api1_IndustryAvgList>> get_Api1_IndustryList(){
        return mRemote.get_Api1_IndustryList();
    }

    @Override
    public Observable<Response<Api1_IndustryAverage>> get_Api1_IndustryAverage(String time,String industry){
        return mRemote.get_Api1_IndustryAverage(time,industry);
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Sms_CheckCode(String phoneNumber, String graphCheckCode) {
        return mRemote.post_Sms_CheckCode(phoneNumber, graphCheckCode);
    }

    @Override
    public  Observable<Response<Api1_post_common>> post_Register_Information(String phoneNumber,String pwd, String MailCode){
        return mRemote.post_Register_Information(phoneNumber, pwd, MailCode);
    }

    @Override
    public  Observable<Response<Api1_post_token>> post_Login_Information(String phoneNumber, String pwd){
        return mRemote.post_Login_Information(phoneNumber,pwd);
    }

    @Override
    public  Observable<Response<Api1_post_common>> post_Personal_Information(String real_name,String nick_name,String institution,
                                                                   String department, String position,String responsibility,
                                                                   String office_phone,String email){
        return  mRemote.post_Personal_Information(real_name,nick_name,institution,department,position,responsibility,office_phone,email);
    }

    @Override
    public  Observable<Response<Api1_post_common>> post_Verify_Mobile(String phoneNumber,String message_code){
        return mRemote.post_Verify_Mobile(phoneNumber,message_code);
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Change_Pwd(String new_pwd){
        return mRemote.post_Change_Pwd(new_pwd);
    }

    @Override
    public Observable<Response<Api1_post_temporary_trust>> post_Temporary_Trust(String state){
        return mRemote.post_Temporary_Trust(state);
    }

    @Override
    public  Observable<Response<Api1_post_common>> post_Modify_Trust(String uuid,String fin_borrower,String fin_borrower_id,String asset_comp,
                                                           String asset_comp_id,String asset_plan_type,String inv_agency,String inv_agency_id,
                                                           String filing_id,String filing,String i_date,String plan_amount, String plan_rate,
                                                           String mana_rate,String term,String guar_way,String main_term_type_id,String main_term_type,
                                                           String main_term){
        return mRemote.post_Modify_Trust(uuid,fin_borrower,fin_borrower_id,asset_comp, asset_comp_id,asset_plan_type,inv_agency,inv_agency_id,
                filing_id,filing,i_date,plan_amount, plan_rate, mana_rate,term,guar_way,main_term_type_id,main_term_type, main_term);
    }

    @Override
    public  Observable<Response<Api1_post_common>> post_Commit_Trust(String fin_borrower,String fin_borrower_id,String asset_comp,String asset_comp_id,
                                                           String asset_plan_type,String inv_agency,String inv_agency_id,String filing_id,String filing,
                                                           String i_date,String plan_amount,String plan_rate,String mana_rate,String term,String guar_way,
                                                           String main_term_type_id,String main_term_type,String main_term){
        return mRemote.post_Commit_Trust(fin_borrower,fin_borrower_id,asset_comp,asset_comp_id, asset_plan_type,inv_agency,inv_agency_id,filing_id,filing,
                i_date,plan_amount,plan_rate,mana_rate,term,guar_way, main_term_type_id,main_term_type,main_term);
    }

    @Override
    public Observable<Response<ResponseBody>> get_Image_Code(){
        return mRemote.get_Image_Code();
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Delete_Trust(String uuid){return mRemote.post_Delete_Trust(uuid);}

    @Override
    public Observable<Response<Api1_Dic_Filling>> get_Dic_Filling(){return mRemote.get_Dic_Filling();}

    @Override
    public  Observable<Response<Api1_Dic_Filling>> get_Main_Term_Type(){return mRemote.get_Main_Term_Type();}

    @Override
    public Observable<Response<Api1_Dic_Filling>> get_Asset_Plan_Type(){return mRemote.get_Asset_Plan_Type();}

    @Override
    public Observable<Response<Api1_Opportunity_Abstract>> get_Opportunity_Abstract(String c_id, String first_level_name){
        return mRemote.get_Opportunity_Abstract(c_id,first_level_name);}

    @Override
    public Observable<Response<Api1_Business_Opportunity>> get_Business_Opportunity(String c_id, String first_level_name){
        return mRemote.get_Business_Opportunity(c_id,first_level_name);
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Collect_Company(String c_id, String name,String record_from){
        return mRemote.post_Collect_Company(c_id,name,record_from);
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Cancel_Collect(String c_id, String name,String record_from){
        return mRemote.post_Cancel_Collect(c_id,name,record_from);
    }

    @Override
    public Observable<Response<Api1_CollectionList>> get_Api1_CollectionList(int limit, int page) {
        return mRemote.get_Api1_CollectionList(limit, page);
    }

    @Override
    public Observable<Response<Api1_NewFirmgraphHolders>> get_New_Firmgraph_Holders(String c_id){
        return mRemote.get_New_Firmgraph_Holders(c_id);
    }

    @Override
    public Observable<Response<Api1_SearchInstitution>> get_Api1_SearchInstitution(String keyword,int limit,int page){
        return mRemote.get_Api1_SearchInstitution(keyword,limit,page);
    }

    @Override
    public Observable<Response<Api1_ShowUserInfo>> get_User_Info(){
        return mRemote.get_User_Info();
    }

    @Override
    public Observable<Response<ResponseBody>> get_Pdf(String c_id, String type){
        return mRemote.get_Pdf(c_id,type);
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Modify_Code(String mobile,String img_code){
        return mRemote.post_Modify_Code(mobile,img_code);
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Search_Behavior(String first_event_id, String keyword, String time){
        return mRemote.post_Search_Behavior(first_event_id, keyword, time);
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Click_Search(String first_event_id, String c_id, String time){
        return mRemote.post_Click_Search(first_event_id, c_id, time);
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Browse_Recode(String first_event_id,String second_event_id, String third_event_id,
                                                                     String c_id,String start_time,String end_time){
        return mRemote.post_Browse_Recode(first_event_id,second_event_id,third_event_id,c_id,start_time,end_time);
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Cancel_Login(String phoneNumber, String graphCheckCode){
        return mRemote.post_Cancel_Login(phoneNumber, graphCheckCode);
    }

    @Override
    public Observable<Response<Api1_FriendsList>> get_Api1_FriendsList(int limit, int page) {
        return mRemote.get_Api1_FriendsList(limit, page);
    }


    @Override
    public Observable<Response<Api1_Search_Users>> get_Api1_Search_Users(String department) {
        return mRemote.get_Api1_Search_Users(department);
    }

    @Override
    public Observable<Response<Api1_Send_Friend_Response>> get_Api1_Send_Friend_Request(String  rec_id) {
        return mRemote.get_Api1_Send_Friend_Request(rec_id);
    }

    @Override
    public Observable<Response<Api1_FriendsList>> get_Api1_Friends_request_list() {
        return mRemote.get_Api1_Friends_request_list();
    }



    @Override
    public Observable<Response<Api1_Receive_Friend>> get_Api1_Receive_friend_request( String rec_id) {
        return mRemote.get_Api1_Receive_friend_request(rec_id);
    }

    @Override
    public Observable<Response<Api1_Send_Friends_Message>> get_Api1_Send_friends_message(String content, String rec_id) {
        return mRemote.get_Api1_Send_friends_message( content,  rec_id);
    }

    @Override
    public Observable<Response<Api1_Receive_Friends_Message>> get_Api1_Receive_friends_message(String send_id) {
        return mRemote.get_Api1_Receive_friends_message( send_id);
    }

    private String keyword;
    private String companyId;
    private String fromPage;
    private String businessOpportunityTag;
    private static SharedPreferences userToken = getContext().getSharedPreferences("IDs",Context.MODE_PRIVATE);
    private boolean loginStatus = userToken.getBoolean("loginStatus",false);        //用于判断是否处于登陆状态
    public static String session= userToken.getString("session","");
    public static String token_id = userToken.getString("token-id","");
    public boolean flag;


    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }//获取搜索结果作为搜索接口的keyword

    public String getKeyword() {
        return keyword;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }//获取搜索结果页面每个item的id

    public String getCompanyId() {
        return companyId;
    }

    public void setFromPage(String from){
        this.fromPage = from;
    }//获取公司列表界面的Intent

    public String getFromPage(){
        return fromPage;
    }

    public void setBusinessOpportunityTag(String tag){this.businessOpportunityTag = tag;}

    public String getBusinessOpportunityTag(){return businessOpportunityTag;}

    public boolean checkSession(String newSession){
        session = userToken.getString("session","");
        if(!newSession.equals(session)) {
            session = newSession;
            SharedPreferences.Editor sessionEditor = userToken.edit();
            sessionEditor.putString("session",session);
            sessionEditor.apply();
            return true;
        }
        return false;
    }

    public boolean checkToken_id(String newToken_id){
        token_id = userToken.getString("token-id","");
        if(!newToken_id.equals(token_id)){
            token_id=newToken_id;
            SharedPreferences.Editor tokenEditor = userToken.edit();
            tokenEditor.putString("token-id",token_id);
            tokenEditor.apply();
            return true;
        }
        return false;
    }

    public void login(){
        loginStatus = true;
        SharedPreferences.Editor tokenEditor = userToken.edit();
        tokenEditor.putBoolean("loginStatus",loginStatus);
        tokenEditor.apply();
    }

    public void logout(){
        loginStatus = false;
        SharedPreferences.Editor tokenEditor = userToken.edit();
        tokenEditor.putBoolean("loginStatus",loginStatus);
        tokenEditor.apply();

        session = "";
        SharedPreferences.Editor sessionEditor = userToken.edit();
        sessionEditor.putString("session",session);
        sessionEditor.apply();

        token_id = "";
        SharedPreferences.Editor tokenidEditor = userToken.edit();
        tokenidEditor.putString("token-id",token_id);
        tokenidEditor.apply();
    }

    public boolean getLoginStatus(){ return loginStatus; }

    public void setFlag(boolean flag){
        this.flag = flag;
    }

    public boolean getFlag(){
        return flag;
    }


}
