package com.demo.reborn.data;

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

public interface FinancialDataInterface {
    Observable<Response<Api1_FirmgraphHolders_single>> get_Api1_FirmgraphHolders_single(String c_id);

    Observable<Response<Api1_FinancingIndex>> get_Api1_FinancingIndex(String c_id);

    Observable<Api1_Test> get_Api1_Test(int gg);

    Observable<Response<Api1_CompanyList>> get_Api1_CompanyList(int limit, int page);

    Observable<Response<Api1_SearchCompany>> get_Api1_SearchCompany(String keyword, String industry, int limit, int page);

    Observable<Response<Api1_SearchIndustry>> get_Api1_SearchIndustry(String industry, int limit, int page);

    Observable<Response<Api1_BaseInfo>> get_Api1_BaseInfo(String c_id);

    Observable<Response<Api1_Holders>> get_Api1_Holders(String c_id);

    Observable<Api1_FirmgraphHolders> get_Api1_FirmgraphHolders(String c_id, String name);

    Observable<Response<Api1_FirmgraphInvestments>> get_Api1_FirmgraphInvestments(String c_id);

    Observable<Response<Api1_Managers>> get_Api1_Managers(String c_id);

    Observable<Response<Api1_ChangeInfo>> get_Api1_ChangeInfo(String c_id);

    Observable<Response<Api1_Business>> get_Api1_Business(String c_id);

    Observable<Response<Api1_FinancialStatement>> get_Api1_FinancialStatement(String c_id,String time);

    Observable<Api1_FinancingInfo> get_Api1_FinancingInfo(String c_id);

    Observable<Api1_FinancingGroupInfo> get_Api1_FinancingGroupInfo(String g_id);

    Observable<Response<Api1_FinancingInfo0729>> get_Api1_FinancingInfo0729(String c_id);

    Observable<Response<Api1_FinancingGroupInfo0729>> get_Api1_FinancingGroupInfo0729(String g_id,String g_name);

    Observable<Response<Api1_IndustryAvgList>> get_Api1_IndustryList();

    Observable<Response<Api1_IndustryAverage>> get_Api1_IndustryAverage(String time,String industry);

    Observable<Response<Api1_post_common>> post_Sms_CheckCode(String phoneNumber, String graphCheckCode);

    Observable<Response<Api1_post_common>> post_Register_Information(String phoneNumber,String pwd, String MailCode);

    Observable<Response<Api1_post_token>> post_Login_Information(String phoneNumber,String pwd);

    Observable<Response<Api1_post_common>> post_Personal_Information(String real_name,String nick_name,String institution,
                                                           String department, String position,String responsibility,
                                                           String office_phone,String email);

    Observable<Response<Api1_post_common>> post_Verify_Mobile(String phoneNumber,String message_code);

    Observable<Response<Api1_post_common>> post_Change_Pwd(String new_pwd);

    Observable<Response<Api1_post_temporary_trust>> post_Temporary_Trust(String state);

    Observable<Response<Api1_post_common>> post_Modify_Trust(String uuid,String fin_borrower,String fin_borrower_id,String asset_comp,
                                                   String asset_comp_id,String asset_plan_type,String inv_agency,String inv_agency_id,
                                                   String filing_id,String filing,String i_date,String plan_amount, String plan_rate,
                                                   String mana_rate,String term,String guar_way,String main_term_type_id,String main_term_type,
                                                   String main_term);

    Observable<Response<Api1_post_common>> post_Commit_Trust(String fin_borrower,String fin_borrower_id,String asset_comp,String asset_comp_id,
                                                   String asset_plan_type,String inv_agency,String inv_agency_id,String filing_id,String filing,
                                                   String i_date,String plan_amount,String plan_rate,String mana_rate,String term,String guar_way,
                                                   String main_term_type_id,String main_term_type,String main_term);

    Observable<Response<ResponseBody>> get_Image_Code();

    Observable<Response<Api1_post_common>> post_Delete_Trust(String uuid);

    Observable<Response<Api1_Dic_Filling>> get_Dic_Filling();

    Observable<Response<Api1_Dic_Filling>> get_Main_Term_Type();

    Observable<Response<Api1_Dic_Filling>> get_Asset_Plan_Type();

    Observable<Response<Api1_Opportunity_Abstract>> get_Opportunity_Abstract(String c_id,String first_level_name);

    Observable<Response<Api1_Business_Opportunity>> get_Business_Opportunity(String c_id,String first_level_name);

    Observable<Response<Api1_post_common>> post_Collect_Company(String c_id,String name,String record_from);

    Observable<Response<Api1_post_common>> post_Cancel_Collect(String c_id,String name,String record_from);

    Observable<Response<Api1_CollectionList>> get_Api1_CollectionList(int limit, int page);


    Observable<Response<Api1_FriendsList>> get_Api1_FriendsList(int limit, int page);

    Observable<Response<Api1_Search_Users>> get_Api1_Search_Users(String department);

    Observable<Response<Api1_Send_Friend_Response>> get_Api1_Send_Friend_Request(int  rec_id);

    Observable<Response<Api1_FriendsList>> get_Api1_Friends_request_list(String send_id);

    Observable<Response<Api1_Receive_Friend>> get_Api1_Receive_friend_request();

    Observable<Response<Api1_Send_Friends_Message>> get_Api1_Send_friends_message(String content, String rec_id);

    Observable<Response<Api1_Receive_Friends_Message>> get_Api1_Receive_friends_message(String send_id);




    Observable<Response<Api1_NewFirmgraphHolders>> get_New_Firmgraph_Holders(String c_id);

    Observable<Response<Api1_SearchInstitution>> get_Api1_SearchInstitution(String keyword,int limit,int page);

    Observable<Response<Api1_ShowUserInfo>> get_User_Info();

    Observable<Response<ResponseBody>> get_Pdf(String c_id, String type);

    Observable<Response<Api1_post_common>> post_Modify_Code(String mobile,String img_code);

    Observable<Response<Api1_post_common>> post_Search_Behavior(String first_event_id, String keyword, String time);

    Observable<Response<Api1_post_common>> post_Click_Search(String first_event_id, String c_id, String time);

    Observable<Response<Api1_post_common>> post_Browse_Recode(String first_event_id,String second_event_id, String third_event_id,
                                                              String c_id,String start_time,String end_time);

    Observable<Response<Api1_post_common>> post_Cancel_Login(String phoneNumber, String graphCheckCode);
}

