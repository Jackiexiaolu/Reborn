package com.demo.reborn.data.remote;

import com.demo.reborn.data.FinancialDataInterface;
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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.demo.reborn.data.FinancialDataRepository.session;
import static com.demo.reborn.data.FinancialDataRepository.token_id;


public class FinancialDataRemote implements FinancialDataInterface {

    private static String BASE_URL = "http://api.qirongbaotech.com/api1/";

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60,TimeUnit.SECONDS).
            writeTimeout(60,TimeUnit.SECONDS).build();

    private static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
    @Override
    public Observable<Response<Api1_FirmgraphHolders_single>> get_Api1_FirmgraphHolders_single(String c_id){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FirmgraphHolders_single(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_FinancingIndex>> get_Api1_FinancingIndex(String c_id){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FinancingIndex(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Api1_Test> get_Api1_Test(int gg) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_gggg(gg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_CompanyList>> get_Api1_CompanyList(int limit, int page) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_CompanyList(session,token_id,limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_SearchCompany>> get_Api1_SearchCompany(String keyword, String industry, int limit, int page) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_SearchCompany(session,token_id,keyword,industry,limit,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_SearchIndustry>> get_Api1_SearchIndustry(String industry, int limit, int page) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_SearchIndustry(session,token_id,industry,limit,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_BaseInfo>> get_Api1_BaseInfo(String c_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_BaseInfo(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Holders>> get_Api1_Holders(String c_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_Holders(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Api1_FirmgraphHolders> get_Api1_FirmgraphHolders(String c_id, String name) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FirmgraphHolders(c_id,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_FirmgraphInvestments>> get_Api1_FirmgraphInvestments(String c_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FirmgraphInvestments(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Managers>> get_Api1_Managers(String c_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_Managers(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_ChangeInfo>> get_Api1_ChangeInfo(String c_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_ChangeInfo(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Business>> get_Api1_Business(String c_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_Business(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_FinancialStatement>> get_Api1_FinancialStatement(String c_id,String time) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FinancialStatement(session,token_id,c_id,time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Api1_FinancingInfo> get_Api1_FinancingInfo(String c_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FinancingInfo(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Api1_FinancingGroupInfo> get_Api1_FinancingGroupInfo(String g_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FinancingGroupInfo(session,token_id,g_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_FinancingInfo0729>> get_Api1_FinancingInfo0729(String c_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FinancingInfo0729(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_FinancingGroupInfo0729>> get_Api1_FinancingGroupInfo0729(String g_id,String g_name) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FinancingGroupInfo0729(session,token_id,g_id,g_name)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_IndustryAvgList>> get_Api1_IndustryList(){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_IndustryList(session,token_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_IndustryAverage>> get_Api1_IndustryAverage(String time,String industry){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_IndustryAverage(session,token_id,time,industry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<Response<Api1_post_common>> post_Sms_CheckCode(String phoneNumber, String graphCheckCode) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Sms_CheckCode(session,token_id,phoneNumber,graphCheckCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public  Observable<Response<Api1_post_common>> post_Register_Information(String phoneNumber,String pwd, String MailCode){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Register_Information(session,token_id,phoneNumber,pwd, MailCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public  Observable<Response<Api1_post_token>> post_Login_Information(String phoneNumber, String pwd){

        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Login_Information(session,token_id,phoneNumber,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Personal_Information(String real_name,String nick_name,String institution,
                                                                  String department, String position,String responsibility,
                                                                  String office_phone,String email){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Personal_Information(session,token_id,real_name,nick_name,institution,department,position,responsibility,office_phone,email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Verify_Mobile(String phoneNumber,String message_code){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Verify_Mobile(session,token_id,phoneNumber,message_code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public  Observable<Response<Api1_post_common>> post_Change_Pwd(String new_pwd){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Change_Pwd(session,token_id,new_pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_temporary_trust>> post_Temporary_Trust(String state){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Temporary_Trust(session,token_id,state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Modify_Trust(String uuid,String fin_borrower,String fin_borrower_id,String asset_comp,
                                                          String asset_comp_id,String asset_plan_type,String inv_agency,String inv_agency_id,
                                                          String filing_id,String filing,String i_date,String plan_amount, String plan_rate,
                                                          String mana_rate,String term,String guar_way,String main_term_type_id,String main_term_type,
                                                          String main_term){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Modify_Trust(session,token_id,uuid,fin_borrower,fin_borrower_id,asset_comp, asset_comp_id,asset_plan_type,inv_agency,inv_agency_id,
                        filing_id,filing,i_date,plan_amount, plan_rate, mana_rate,term,guar_way,main_term_type_id,main_term_type, main_term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Commit_Trust(String fin_borrower,String fin_borrower_id,String asset_comp,String asset_comp_id,
                                                          String asset_plan_type,String inv_agency,String inv_agency_id,String filing_id,String filing,
                                                          String i_date,String plan_amount,String plan_rate,String mana_rate,String term,String guar_way,
                                                          String main_term_type_id,String main_term_type,String main_term){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Commit_Trust(session,token_id,fin_borrower,fin_borrower_id,asset_comp,asset_comp_id, asset_plan_type,inv_agency,inv_agency_id,filing_id,filing,
                        i_date,plan_amount,plan_rate,mana_rate,term,guar_way, main_term_type_id,main_term_type,main_term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<ResponseBody>> get_Image_Code(){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Image_Code(session,token_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Delete_Trust(String uuid){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Delete_Trust(session,token_id,uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Dic_Filling>> get_Dic_Filling(){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Dic_Filling(session,token_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public  Observable<Response<Api1_Dic_Filling>> get_Main_Term_Type(){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Main_Term_Type(session,token_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Dic_Filling>> get_Asset_Plan_Type(){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Asset_Plan_Type(session,token_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Opportunity_Abstract>> get_Opportunity_Abstract(String c_id, String first_level_name){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Opportunity_Abstract(session,token_id,c_id,first_level_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Business_Opportunity>> get_Business_Opportunity(String c_id, String first_level_name){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Business_Opportunity(session,token_id,c_id,first_level_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Collect_Company(String c_id,String name,String record_from){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Collect_Company(session,token_id,c_id,name,record_from)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Cancel_Collect(String c_id,String name,String record_from){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Cancel_Collect(session,token_id,c_id,name,record_from)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_CollectionList>> get_Api1_CollectionList(int limit, int page) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_CollectionList(session,token_id,limit,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_NewFirmgraphHolders>> get_New_Firmgraph_Holders(String c_id){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_New_Firmgraph_Holders(session,token_id,c_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_SearchInstitution>> get_Api1_SearchInstitution(String keyword,int limit,int page){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_SearchInstituion(session,token_id,keyword,limit,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_ShowUserInfo>> get_User_Info(){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_User_Info(session,token_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<ResponseBody>> get_Pdf(String c_id, String type){
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Pdf(session,token_id,c_id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Modify_Code(String mobile,String img_code){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Modify_Code(session,token_id,mobile,img_code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Search_Behavior(String first_event_id, String keyword, String time){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Search_Behavior(session,token_id, first_event_id, keyword, time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Click_Search(String first_event_id, String c_id, String time){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Click_Search(session,token_id, first_event_id, c_id, time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Browse_Recode(String first_event_id,String second_event_id, String third_event_id,
                                                                     String c_id,String start_time,String end_time){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Browse_Recode(session,token_id,first_event_id,second_event_id,third_event_id,c_id,start_time,end_time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_post_common>> post_Cancel_Login(String phoneNumber,String graphCheckCode){
        return getRetrofit()
                .create(FinancialDataService.class)
                .post_Cancel_Login(session,token_id,phoneNumber,graphCheckCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    @Override
    public Observable<Response<Api1_FriendsList>> get_Api1_FriendsList(int limit, int page) {
        System.out.println("token_id------22222222-------"+token_id);
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_FriendsList(session,token_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Search_Users>> get_Api1_Search_Users(String department) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_Search_users(session,token_id,department)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Send_Friend_Response>> get_Api1_Send_Friend_Request(String rec_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_Send_friend_request(session,token_id,rec_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_FriendsList>> get_Api1_Friends_request_list(String send_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_Receive_friend_request(session,token_id,send_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Receive_Friend>> get_Api1_Receive_friend_request() {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_Friends_request_list(session,token_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Send_Friends_Message>> get_Api1_Send_friends_message(String content, String rec_id) {
        System.out.println("get_Api1_Send_friends_message-------------"+token_id);
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_Send_friends_message(session,token_id,content,rec_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Response<Api1_Receive_Friends_Message>> get_Api1_Receive_friends_message(String send_id) {
        return getRetrofit()
                .create(FinancialDataService.class)
                .get_Api1_Receive_friends_message(session,token_id,send_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
