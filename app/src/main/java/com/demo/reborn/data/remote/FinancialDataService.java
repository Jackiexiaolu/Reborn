package com.demo.reborn.data.remote;

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

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface FinancialDataService {
    @GET("firmgraph_holders")
    Observable<Response<Api1_FirmgraphHolders_single>> get_Api1_FirmgraphHolders_single(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("financing_index")
    Observable<Response<Api1_FinancingIndex>> get_Api1_FinancingIndex(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("test")
    Observable<Api1_Test> get_gggg(
            @Query("gg") int gg
    );

    @GET("companylist")
    Observable<Response<Api1_CompanyList>> get_Api1_CompanyList(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("limit") int limit,
            @Query("page") int page
    );

    @GET("search_company")
    Observable<Response<Api1_SearchCompany>> get_Api1_SearchCompany(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("keyword") String keyword,
            @Query("industry") String industry,
            @Query("limit") int limit,
            @Query("page") int page
    );

    @GET("search_industry")
    Observable<Response<Api1_SearchIndustry>> get_Api1_SearchIndustry(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("industry") String industry,
            @Query("limit") int limit,
            @Query("page") int page
    );

    @GET("baseinfo")
    Observable<Response<Api1_BaseInfo>> get_Api1_BaseInfo(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("holders")
    Observable<Response<Api1_Holders>> get_Api1_Holders(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("firmgraph_holders")
    Observable<Api1_FirmgraphHolders> get_Api1_FirmgraphHolders(
            @Query("c_id") String c_id,
            @Query("name") String name
    );

    @GET("firmgraph_investments")
    Observable<Response<Api1_FirmgraphInvestments>> get_Api1_FirmgraphInvestments(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("managers")
    Observable<Response<Api1_Managers>> get_Api1_Managers(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("changeinfo")
    Observable<Response<Api1_ChangeInfo>> get_Api1_ChangeInfo(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("business")
    Observable<Response<Api1_Business>> get_Api1_Business(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("financialstatement")
    Observable<Response<Api1_FinancialStatement>> get_Api1_FinancialStatement(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id,
            @Query("time") String time
    );

    @GET("financing_info")
    Observable<Api1_FinancingInfo> get_Api1_FinancingInfo(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("financing_group_info")
    Observable<Api1_FinancingGroupInfo> get_Api1_FinancingGroupInfo(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("g_id") String g_id
    );

    @GET("financing_info_0729")
    Observable<Response<Api1_FinancingInfo0729>> get_Api1_FinancingInfo0729(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("financing_group_info_0729")
    Observable<Response<Api1_FinancingGroupInfo0729>> get_Api1_FinancingGroupInfo0729(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("g_id") String g_id,
            @Query("g_name") String g_name
    );

    @GET("industry_avg_list")
    Observable<Response<Api1_IndustryAvgList>> get_Api1_IndustryList(
            @Header("Cookie") String session,
            @Header("token_id") String token_id
    );

    @GET("industry_avg")
    Observable<Response<Api1_IndustryAverage>> get_Api1_IndustryAverage(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("time") String time,
            @Query("industry") String industry
    );

    @FormUrlEncoded
    @POST("message_code")
    Observable<Response<Api1_post_common>> post_Sms_CheckCode(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("mobile") String phoneNumber,
            @Field("img_code") String graphCheckCode);

    @FormUrlEncoded
    @POST("register")
    Observable<Response<Api1_post_common>> post_Register_Information(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("mobile") String phoneNumber,
            @Field("pwd") String pwd,
            @Field("message_code") String MailCode
    );

    @FormUrlEncoded
    @POST("login")
    Observable<Response<Api1_post_token>> post_Login_Information(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("mobile") String phoneNumber,
            @Field("pwd") String pwd
    );

    @FormUrlEncoded
    @POST("personal_information")
    Observable<Response<Api1_post_common>> post_Personal_Information(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("real_name") String real_name,
            @Field("nick_name") String nick_name,
            @Field("institution") String institution,
            @Field("department") String department,
            @Field("position") String position,
            @Field("responsibility") String responsibility,
            @Field("office_phone") String office_phone,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("verify_mobile")
    Observable<Response<Api1_post_common>> post_Verify_Mobile(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("mobile") String phoneNumber,
            @Field("message_code") String message_code
    );

    @FormUrlEncoded
    @POST("change_pwd")
    Observable<Response<Api1_post_common>> post_Change_Pwd(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("new_pwd") String new_pwd
    );

    @FormUrlEncoded
    @POST("temporary_trust")
    Observable<Response<Api1_post_temporary_trust>> post_Temporary_Trust(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("state") String state
    );

    @FormUrlEncoded
    @POST("modify_trust")
    Observable<Response<Api1_post_common>> post_Modify_Trust(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("uuid") String uuid,
            @Field("fin_borrower") String fin_borrower,
            @Field("fin_borrower_id") String fin_borrower_id,
            @Field("asset_comp") String asset_comp,
            @Field("asset_comp_id") String asset_comp_id,
            @Field("asset_plan_type") String asset_plan_type,
            @Field("inv_agency") String inv_agency,
            @Field("inv_agency_id") String inv_agency_id,
            @Field("filing_id") String filing_id,
            @Field("filing") String filing,
            @Field("i_date") String idate,
            @Field("plan_amount") String plan_amount,
            @Field("plan_rate") String plan_rate,
            @Field("mana_rate") String mana_rate,
            @Field("term") String term,
            @Field("guar_way") String guar_way,
            @Field("main_term_type_id") String main_term_type_id,
            @Field("main_term_type") String main_term_type,
            @Field("main_term") String main_term
    );

    @FormUrlEncoded
    @POST("commit_trust")
    Observable<Response<Api1_post_common>> post_Commit_Trust(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("fin_borrower") String fin_borrower,
            @Field("fin_borrower_id") String fin_borrower_id,
            @Field("asset_comp") String asset_comp,
            @Field("asset_comp_id") String asset_comp_id,
            @Field("asset_plan_type") String asset_plan_type,
            @Field("inv_agency") String inv_agency,
            @Field("inv_agency_id") String inv_agency_id,
            @Field("filing_id") String filing_id,
            @Field("filing") String filing,
            @Field("i_date") String i_date,
            @Field("plan_amount") String plan_amount,
            @Field("plan_rate") String plan_rate,
            @Field("mana_rate") String mana_rate,
            @Field("term") String term,
            @Field("guar_way") String guar_way,
            @Field("main_term_type_id") String main_term_type_id,
            @Field("main_term_type") String main_term_type,
            @Field("main_term") String main_term
    );

    @GET("img_code")
    Observable<Response<ResponseBody>> get_Image_Code(
            @Header("Cookie") String session,
            @Header("token_id") String token_id
    );

    @FormUrlEncoded
    @POST("delete_trust")
    Observable<Response<Api1_post_common>> post_Delete_Trust(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("uuid") String uuid
    );

    @GET("dic_filling")
    Observable<Response<Api1_Dic_Filling>> get_Dic_Filling(
            @Header("Cookie") String session,
            @Header("token_id") String token_id
    );

    @GET("main_term_type")
    Observable<Response<Api1_Dic_Filling>> get_Main_Term_Type(
            @Header("Cookie") String session,
            @Header("token_id") String token_id
    );

    @GET("asset_plan_type")
    Observable<Response<Api1_Dic_Filling>> get_Asset_Plan_Type(
            @Header("Cookie") String session,
            @Header("token_id") String token_id
    );

    @GET("opportunity_abstract")
    Observable<Response<Api1_Opportunity_Abstract>> get_Opportunity_Abstract(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id,
            @Query("first_level_name") String first_level_name
    );

    @GET("business_opportunity")
    Observable<Response<Api1_Business_Opportunity>> get_Business_Opportunity(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id,
            @Query("first_level_name") String first_level_name
    );

    @FormUrlEncoded
    @POST("collect_company")
    Observable<Response<Api1_post_common>> post_Collect_Company(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("c_id") String c_id,
            @Field("name") String name,
            @Field("record_from") String record_name
    );

    @FormUrlEncoded
    @POST("cancel_collect")
    Observable<Response<Api1_post_common>> post_Cancel_Collect(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("c_id") String c_id,
            @Field("name") String name,
            @Field("record_from") String record_name
    );

    @GET("collection_list")
    Observable<Response<Api1_CollectionList>> get_Api1_CollectionList(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("limit") int limit,
            @Query("page") int page
    );

    @GET("new_firmgraph_holders")
    Observable<Response<Api1_NewFirmgraphHolders>> get_New_Firmgraph_Holders(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id
    );

    @GET("search_institution")
    Observable<Response<Api1_SearchInstitution>> get_Api1_SearchInstituion(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("keyword") String keyword,
            @Query("limit") int limit,
            @Query("page") int page
    );

    @GET("show_user_info")
    Observable<Response<Api1_ShowUserInfo>> get_User_Info(
            @Header("Cookie") String session,
            @Header("token_id") String token_id
    );

//    @Streaming
    @GET("get_pdf")
    Observable<Response<ResponseBody>> get_Pdf(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("c_id") String c_id,
            @Query("type") String type
    );

    @FormUrlEncoded
    @POST("modify_code")
    Observable<Response<Api1_post_common>> post_Modify_Code(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("mobile") String mobile,
            @Field("img_code") String img_code
    );

    @FormUrlEncoded
    @POST("user_search")
    Observable<Response<Api1_post_common>> post_Search_Behavior(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("first_event_id") String first_event_id,
            @Field("keyword") String keyword,
            @Field("time") String time
    );

    @FormUrlEncoded
    @POST("user_click")
    Observable<Response<Api1_post_common>> post_Click_Search(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("first_event_id") String first_event_id,
            @Field("c_id") String c_id,
            @Field("time") String time
    );

    @FormUrlEncoded
    @POST("user_browse")
    Observable<Response<Api1_post_common>> post_Browse_Recode(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("first_event_id")String first_event_id,
            @Field("second_event_id")String second_event_id,
            @Field("third_event_id") String third_event_id,
            @Field("c_id") String c_id,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time
    );

    @FormUrlEncoded
    @POST("cancel_login")
    Observable<Response<Api1_post_common>> post_Cancel_Login(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Field("mobile")String phoneNumber,
            @Field("img_code")String graphCheckCode
    );

    @GET("friends_list")
    Observable<Response<Api1_FriendsList>> get_Api1_FriendsList(
            @Header("Cookie") String session,
            @Header("token_id") String token_id
    );

    @GET("search_users")
    Observable<Response<Api1_Search_Users>> get_Api1_Search_users(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("department") String department
    );

    @GET("send_friend_request")
    Observable<Response<Api1_Send_Friend_Response>> get_Api1_Send_friend_request(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("rec_id") int rec_id
    );

    @GET("receive_friend_request")
    Observable<Response<Api1_FriendsList>> get_Api1_Receive_friend_request(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("send_id") String send_id
    );

    @GET("friends_request_list")
    Observable<Response<Api1_Receive_Friend>> get_Api1_Friends_request_list(
            @Header("Cookie") String session,
            @Header("token_id") String token_id
    );

    @FormUrlEncoded
    @POST("send_friends_message")
    Observable<Response<Api1_Send_Friends_Message>> get_Api1_Send_friends_message(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("content") String content,
            @Query("rec_id") String rec_id
    );


    @GET("receive_friends_message")
    Observable<Response<Api1_Receive_Friends_Message>> get_Api1_Receive_friends_message(
            @Header("Cookie") String session,
            @Header("token_id") String token_id,
            @Query("send_id") String send_id
    );

}
