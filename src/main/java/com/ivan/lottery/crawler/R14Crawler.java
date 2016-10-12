package com.ivan.lottery.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 体彩任14数据抓取
 * */
public class R14Crawler implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(0);
	
	@Override
	public void process(Page page) {
		
//		page.putField("bet_period", page.getHtml().$("#bet_period","text"));
//		page.putField("id", page.getHtml().$("#game_list"));
		List<String> dataList=page.getHtml().$("#game_list tbody tr").all();
		Li
		List<Map<String, String>> betList=new ArrayList<>();
		for(String str:dataList){
			Html strHtml=new Html(str);
			System.out.println(strHtml);
			System.out.println(strHtml.$("td"));
		}
	}

	@Override
	public Site getSite() {
		return site;
	}
	
    public static void main(String[] args) {
        Spider.create(new R14Crawler()).addUrl("http://caipiao.163.com/order/sfc/").thread(1).run();
    }

}
