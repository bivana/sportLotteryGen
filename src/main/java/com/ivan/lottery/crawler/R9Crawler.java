package com.ivan.lottery.crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ivan.lottery.bean.Game;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 体彩任9数据抓取
 * */
public class R9Crawler implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(0);
	
	@Override
	public void process(Page page) {
		try {
			page.putField("period", page.getHtml().$("#bet_period","text").get());
			page.putField("betTime", page.getHtml().$("#bet_time","text").get().substring(6));
			int size=page.getHtml().$("#game_list tbody tr").all().size();
			List<Game> betList=new ArrayList<Game>();
			for(int i=1;i<=size;i++){
				Game game=new Game();
				//序号
				game.setSeq(Integer.parseInt(page.getHtml().$("#game_list tbody tr:nth-child("+i+") td:nth-child(1)","text").get()));
				//赛事
				game.setLeague(page.getHtml().$("#game_list tbody tr:nth-child("+i+") td:nth-child(2)","text").get());
				//开赛时间
				game.setDate(page.getHtml().$("#game_list tbody tr:nth-child("+i+") td:nth-child(3)","text").get());
				//home主队
				game.setHome(page.getHtml().$("#game_list tbody tr:nth-child("+i+") td:nth-child(4) a:nth-of-type(1)","text").get());
				//guest客队
				game.setGuest(page.getHtml().$("#game_list tbody tr:nth-child("+i+") td:nth-child(4) a:nth-of-type(2)","text").get());
				//赔率
				String oddsStr=page.getHtml().$("#game_list tbody tr:nth-child("+i+") td:nth-child(7)","text").get();
				String[] odds=oddsStr.split("\\|");
				game.setWinOdds(Double.parseDouble(odds[0]));
				game.setEqOdds(Double.parseDouble(odds[1]));
				game.setLoseOdds(Double.parseDouble(odds[2]));
				System.out.println(game.show());
				betList.add(game);
			}
			page.putField("betList", betList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Site getSite() {
		return site;
	}
	
    public static void main(String[] args) {
        Spider.create(new R9Crawler()).addUrl("http://caipiao.163.com/order/rx9/")
        .addPipeline(new Pipeline() {
			
			@Override
			@SuppressWarnings("unchecked")
			public void process(ResultItems resultItems, Task task) {
				try {
					PrintWriter printWriter = new PrintWriter(new FileWriter(new File("/Users/ivan/Desktop/workspace/test/r9-"+resultItems.get("period")+"-"+System.currentTimeMillis()+".text")));
		            printWriter.write("任9第"+resultItems.get("period")+"期结果如下:\n");
		            List<Game> betList=(ArrayList<Game>)resultItems.get("betList");
		            for(Game game:betList){
		            	printWriter.write(game+"\n");
		            }
		            printWriter.write("\n\n\n\n\n\n\n");
		            
		            for(int i=1;i<=10;i++){
		            	printWriter.write("第"+i+"单{");
		            	List<Game> list=new ArrayList<>();
		            	list.addAll(betList);
		            	List<Game> gameList=new ArrayList<>();
		            	for(int j=1;j<=9;j++){//14场任选9场
		            		gameList.add(list.remove((int)(Math.floor(Math.random()*list.size()))));
		            		
		            	}
		            	Collections.sort(gameList, new Comparator<Game>() {
							@Override
							public int compare(Game g1, Game g2) {
								return new Integer(g1.getSeq()).compareTo(new Integer(g2.getSeq()));
							}
						});
		            	for(Game game:gameList){
		            		printWriter.write(game.getSeq()+":"+game.getResult()+",");
		            	}
		            	printWriter.write("}\n");
		            }
		            printWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		})
        .
        thread(1).run();
    }

}
