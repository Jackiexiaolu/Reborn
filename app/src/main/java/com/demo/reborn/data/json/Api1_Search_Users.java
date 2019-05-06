package com.demo.reborn.data.json;

import java.util.List;

/**
 * Created by hanwenlong on 2019/4/25.
 */

public class Api1_Search_Users {
    public static class UserInfo {
        //// TODO: 2019/4/25  
        public String real_name;
        public String nick_name;
        public String institution;
        public String department;
        public String position;
        public String responsibility;
        public String office_phone;
        public String email;
        public String card;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getResponsibility() {
            return responsibility;
        }

        public void setResponsibility(String responsibility) {
            this.responsibility = responsibility;
        }

        public String getOffice_phone() {
            return office_phone;
        }

        public void setOffice_phone(String office_phone) {
            this.office_phone = office_phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }
    }
    public String info;
//    public List<Api1_ShowUserInfo.UserInfo> select_list;
    public List<List<String>> select_list;
    public int error;
}
