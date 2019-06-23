package com.yanshun.mfluitmarket.common.util;

import android.text.TextUtils;
import android.util.Log;

import com.yanshun.mfluitmarket.common.entity.City;
import com.yanshun.mfluitmarket.common.entity.County;
import com.yanshun.mfluitmarket.common.entity.Province;
import com.yanshun.mfluitmarket.common.entity.Towns;
import com.yanshun.mfluitmarket.common.entity.Village;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.DbManager;
import org.xutils.x;

import java.util.List;

/**
 * Created by hasee on 2017/10/29.
 * 根据国家统计局获取省市县乡镇村
 */

public class AddressGetUtils {
    private String BASE_URL_CHINA = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/";//全国
    private String BASE_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/43/4310.html";//湖南郴州
    private DbManager db;

    private static class LazyHolder{
        private static final AddressGetUtils INSTANCE = new AddressGetUtils();
    }

    public static final AddressGetUtils getInstance(){
        return AddressGetUtils.LazyHolder.INSTANCE;
    }

    public AddressGetUtils(){
        db = DbAddressUtils.getInstance().db;
    }

    /**
     * 获取省份
     */
    public void getProvince(){
        x.task().run(runProvince);
    }
    Runnable runProvince = new Runnable() {
        @Override
        public void run() {
            try {
                List<Province> provincesTemp = db.selector(Province.class).findAll();
                if (null !=provincesTemp && provincesTemp.size() != 0){
                    db.delete(provincesTemp);
                }
                Document doc = Jsoup.connect(BASE_URL).get();
                Elements links = doc.getElementsByClass("provincetr");
                for (Element e : links) {
                    Elements elements = e.getElementsByAttribute("href");
                    for (Element e1:elements){
//                        addrContent.add(e1.select("a").attr("href") + "-" + e1.text());
                        Province province = new Province();
                        String intString = e1.select("a").attr("href").substring(0,2);
                        province.setProviceId(intString);
                        province.setName(e1.text());
                        province.setUrl(e1.select("a").attr("href"));
//                        provinces.add(province);
                        db.save(province);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 获取城市
     */
    public void getCity(){
        x.task().run(runCity);
    }
    Runnable runCity = new Runnable() {
        @Override
        public void run() {
            try {
                List<City> citytemp = db.selector(City.class).findAll();
                if (null !=citytemp && citytemp.size() != 0){
                    db.delete(citytemp);
                }
                List<Province> provicesTmep = db.selector(Province.class).findAll();
                for (int n = 0,m = provicesTmep.size(); n < m;n++){
                    Province province = provicesTmep.get(n);
                    String address =  BASE_URL + province.getUrl().split("-")[0];
                    Document doc = Jsoup.connect(address).get();
                    Elements links = doc.getElementsByClass("citytr");
                    for (Element e : links) {
                        Elements elements = e.getElementsByAttribute("href");
                        for (int i =0,j = elements.size();i< j;i++){
                            if (i==1){
                                City city = new City();
                                city.setProvinceId(province.getProviceId());
                                city.setName(elements.get(i).text());
//                                Log.d("HHHHHH",elements.get(i).text());
                                String cityid = elements.get(i).select("a").attr("href").split("/")[1].substring(0,4);
                                city.setCityId(cityid);
                                city.setUrl(elements.get(i).select("a").attr("href"));
//                                cities.add(city);
                                db.save(city);
                            }
                        }
                    }
                    Thread.sleep(1000 * 5);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 获取县城
     */
    public void getCountry(){
        x.task().run(runCountry);
    }
    Runnable runCountry = new Runnable() {
        @Override
        public void run() {
            try {
                int t = 0;
                List<City> citytemp = db.selector(City.class).findAll();
                if (null != citytemp){
                    if (0 == (citytemp.size() %100)){
                        t  = citytemp.size() /100;
                    }else {
                        t  = (citytemp.size() /100) + 1;
                    }
                }
                List<County> countiestmp = db.selector(County.class).findAll();
                if (null != countiestmp && countiestmp.size() != 0){
                    db.delete(countiestmp);
                }
                for (int z = 0; z < 1;z++ ){
                    int start = z * 100;
                    int end = 0;
                    if (z == t-1){
                        end = citytemp.size();
                    }else{
                        end = (z + 1)* 100;
                    }
                    for (int m = start,n = end; m < n;m++){
                        City city = citytemp.get(m);
                        String address =  BASE_URL ;
                        Document doc = Jsoup.connect(address).get();
                        Elements links = doc.getElementsByClass("countytr");
                        Log.d("HHHHHHCity",city.getUrl() + city.getName());
                        for (Element e : links) {
                            Elements elements = e.getElementsByTag("td");
                            for (int i =0,j = elements.size();i< j;i++){
                                if (i==1){
                                    County county = new County();
                                    county.setCityId(city.getCityId());
                                    Log.d("HHHHHH",elements.get(i).text());
                                    String countyId = "";
                                    if (null == elements.get(i).select("a").attr("href") ||
                                            TextUtils.equals("",elements.get(i).select("a").attr("href"))){
                                        county.setCountyId(elements.get(i).getElementsByTag("td").text());
                                        county.setName("市区");
                                    }else{
                                        countyId = elements.get(i).select("a").attr("href").split("/")[1].substring(0,6);
                                        county.setCountyId(countyId);
                                        county.setName(elements.get(i).text());
                                    }
                                    county.setUrl(elements.get(i).select("a").attr("href"));
//                                    countries.add(county);
                                    db.save(county);
                                }
                            }
                        }
                        Thread.sleep(1000 * 5);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 获取乡镇
     */
    public void getTowns(){
        x.task().run(runTowns);
    }
    Runnable runTowns = new Runnable() {
        @Override
        public void run() {
            try {
                int t = 0;
                int y = 0;
                int x = 0;
                List<County> countiestmp = db.selector(County.class).findAll();
                for (int k = 0;k < countiestmp.size(); k++){
                    if ("08/360830.html".contains(countiestmp.get(k).getCountyId())){
                        y = countiestmp.size() - k;
                        x = k + 1;
                        break;
                    }else{
                        y = countiestmp.size();
                    }
                }
//                y = countiestmp.size();
                if (null != countiestmp){
                    if (0 == (y %100)){
                        t  = y /100;
                    }else {
                        t  = (y /100) + 1;
                    }
                }
                List<Towns> townses = db.selector(Towns.class).findAll();
                if (null != townses && townses.size() != 0){
                    db.delete(townses);
                }
                for (int z = 0; z < t;z++ ) {
                    int start = x + z * 100;
                    int end = 0;
                    if (z == t - 1) {
                        end = countiestmp.size();
                    } else {
                        end = x + (z + 1) * 100;
                    }
                    for (int m =start,n = end; m < n;m++){
                        County county = countiestmp.get(m);
                        if (null == county.getUrl() || TextUtils.equals("",county.getUrl())){
                            continue;
                        }
                        String address =  BASE_URL_CHINA + county.getUrl().split("-")[0].split("/")[1].substring(0,2) +"/"+ county.getUrl().split("-")[0];
                        Document doc = Jsoup.connect(address).get();
                        Elements links = doc.getElementsByClass("towntr");
                        Log.e("HHHHH",county.getUrl() +"---------------" + county.getName() + m);
                        for (Element e : links) {
                            Elements elements = e.getElementsByAttribute("href");
                            for (int i =0,j = elements.size();i< j;i++){
                                if (i==1){
                                    Towns towns = new Towns();
                                    towns.setCountyId(county.getCountyId());
                                    towns.setName(elements.get(i).text());
                                    Log.e("HHHHH",elements.get(i).text());
                                    String townsId = elements.get(i).select("a").attr("href").split("/")[1].substring(0,9);
                                    towns.setTownsId(townsId);
                                    towns.setUrl(elements.get(i).select("a").attr("href"));
                                    db.save(towns);
                                }
                            }
                        }
                        Thread.sleep(1000 * 5);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 获取村委
     */
    public void getVillage(){
        x.task().run(runVillage);
    }
    Runnable runVillage = new Runnable() {
        @Override
        public void run() {
            try {
                int t = 0;
                int y = 0;
                int x = 0;
                List<Towns> townsesTemp = db.selector(Towns.class).findAll();
                for (int k = 0;k < townsesTemp.size(); k++){
                    if ("26/150526105.html".contains(townsesTemp.get(k).getCountyId())){
                        y = townsesTemp.size() - k;
                        x = k + 1;
                        break;
                    }
                }
//                y = townsesTemp.size();
                if (null != townsesTemp){
                    if (0 == (y %100)){
                        t  = y /100;
                    }else {
                        t  = (y /100) + 1;
                    }
                }
                List<Village> villages = db.selector(Village.class).findAll();
                if (null != villages && villages.size() != 0){
                    db.delete(villages);
                }
                for (int z = 0; z < t;z++ ) {
                    int start = x + z * 100;
                    int end = 0;
                    if (z == t - 1) {
                        end = townsesTemp.size();
                    } else {
                        end = x + (z + 1) * 100;
                    }
                    for (int m =start,n = end; m < n;m++){
                        Towns towns = townsesTemp.get(m);
                        String address =  BASE_URL_CHINA + towns.getUrl().split("-")[0].split("/")[1].substring(0,2) +"/" +towns.getUrl().split("-")[0].split("/")[1].substring(2,4) +"/"+ towns.getUrl().split("-")[0];
                        Document doc = Jsoup.connect(address).get();
                        Elements links = doc.getElementsByClass("villagetr");
                        Log.e("HHHHH",towns.getUrl() +"---------------" + towns.getName() + m);
                        for (Element e : links) {
                            Elements elements = e.getElementsByTag("td");
                            Village village = new Village();
                            village.setTownsId(towns.getTownsId());
                            for (int i =0,j = elements.size();i< j;i++){
                                if (i==0){
                                    village.setVillageId(elements.get(i).text());
                                }
                                if (i==2){
                                    village.setName(elements.get(i).text());
                                    Log.e("HHHHH",elements.get(i).text());
                                    db.saveOrUpdate(village);
                                }
                            }
                        }
                        Thread.sleep(1000 * 5);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
