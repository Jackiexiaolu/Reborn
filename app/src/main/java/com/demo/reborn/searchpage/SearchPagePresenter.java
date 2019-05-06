package com.demo.reborn.searchpage;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.demo.reborn.R;
import com.demo.reborn.RecordSQLiteOpenHelper;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_SearchCompany;
import com.demo.reborn.data.json.Api1_post_common;
import com.demo.reborn.util.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.util.MyApplication.getContext;
import static java.lang.Integer.valueOf;


public class SearchPagePresenter implements SearchPageContract.Presenter {

    private SearchPageContract.View mView;
    private final FinancialDataRepository mData;
    //创建RecordSQLiteOpenHelper对象
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(getContext());
    private BaseAdapter adapter;
    public List<Map<String, Object>> list = new ArrayList<>();
    private int count = 0;
    private int page =0;
    private int limit=0;

    public SearchPagePresenter(SearchPageContract.View View) {
        mData = FinancialDataRepository.getINSTANCE();
        mView = View;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }


    /**
     * 查找数据是否存在于数据库中
     **/
    @Override
    public boolean hasData(String tempName) {

        //cursor是查找到的元素的行的集合(所有满足条件的行)，初始位置是在-1，所以要movetonext！


        // 从数据库中Record表里找到name=tempName的id


        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个

        return cursor.moveToNext();
    }


    /**
     * 模糊查询 & 显示搜索记录
     */

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void queryData(String tempName, ListView listview) {
        //查找任意位置包含tempname的任意值，并对结果进行降序排序
        //采用通配符%进行模糊查询
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id ,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象将cursor对象填充入listview
        adapter = new SimpleCursorAdapter(getContext(), R.layout.searchpage_item_record, cursor, new String[]{"name"},
                new int[]{R.id.tv_record}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 插入数据
     */
    @Override
    public void insertData(String tempName) {
        //真正创建 or 打开数据库

        SQLiteDatabase db = helper.getWritableDatabase();

        if (!(tempName.equals(""))) {
            helper.getWritableDatabase().execSQL(" insert into records(name) values('" + tempName + "')");
            helper.getWritableDatabase().execSQL("delete from records where (select count(name) from records   " +
                    ")> 15 and name in (select name from records order by id desc limit (select count(name) from records) offset 15 )  ");//当搜索记录超过15条时删除最旧的一条
            helper.getWritableDatabase().close();
        }


    }

    /**
     * 清空数据库
     */
    public void deleteData() {
        helper.getWritableDatabase().execSQL("delete from records");
        helper.getWritableDatabase().close();
    }


    public void renewData(String tempName) {
        helper.getWritableDatabase().execSQL("delete from records where name= '" + tempName + "'");
        helper.getWritableDatabase().close();
        insertData(tempName);

    }

    public void setSearchResult(String searchResult){
        mData.setKeyword(searchResult);
    }

    public void setFromPage(String from){
        mData.setFromPage(from);
    }

    /**
     * 模糊查询 & 显示远程数据库中内容
     */
    @Override
    public void getInfo(String info,ListView listView){
        mData.get_Api1_SearchCompany(info, "", 0, 0)//getcount
                .subscribe(new Observer<Response<Api1_SearchCompany>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    //获取完所有的数据，执行OnNext
                    public void onNext(Response<Api1_SearchCompany> api1_searchCompany) {
//                        Log.d("搜索",info);
                        if(isNullOrEmpty(api1_searchCompany.body().count))
                            count =0 ;
                        else
                            count = valueOf(api1_searchCompany.body().count);

//                        Log.d("getcount", String.valueOf(count));

                        if(count == 0)
                            limit = 0;
                        else if(count < 30)
                            limit = count;
                        else
                            limit = 30;

                        mData.get_Api1_SearchCompany(info, "", limit, 0)//新启动一个线程
                                .subscribe(new Observer<Response<Api1_SearchCompany>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    //获取完所有的数据，执行OnNext
                                    public void onNext(Response<Api1_SearchCompany> api1_searchCompany) {
                                        if(limit!=0) {
                                            for (int i = 0; i < limit; i++) {
                                                Map<String, Object> map = new HashMap<>();
                                                map.put("name", api1_searchCompany.body().result.get(i).get(1));
                                                list.add(map);
                                            }
                                        }

                                            final SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.searchpage_item_record, new String[]{"name"}, new int[]{R.id.tv_record});
                                            listView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setSearchRecord(String keyword,String time){
        mData.post_Search_Behavior("1",keyword,time)
                .subscribe(new Observer<Response<Api1_post_common>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_post_common> api1_post_common) {
                        if(api1_post_common.headers().get("Set-Cookie") != null){
                            mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                        }
                        Log.e("info", (api1_post_common.body().info));

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}


