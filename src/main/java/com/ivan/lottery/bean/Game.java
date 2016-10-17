package com.ivan.lottery.bean;

/**
 * 一场比赛
 * */
public class Game {
	//序号
	private int seq;
	//所属联赛
	private String league;
	//比赛日期
	private String date;
	//home主队
	private String home;
	//guest客队
	private String guest;
	//胜赔率
	private double winOdds;
	//平赔率
	private double eqOdds;
	//负赔率
	private double loseOdds;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getLeague() {
		return league;
	}
	public void setLeague(String league) {
		this.league = league;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getGuest() {
		return guest;
	}
	public void setGuest(String guest) {
		this.guest = guest;
	}
	public double getWinOdds() {
		return winOdds;
	}
	public void setWinOdds(double winOdds) {
		this.winOdds = winOdds;
	}
	public double getEqOdds() {
		return eqOdds;
	}
	public void setEqOdds(double eqOdds) {
		this.eqOdds = eqOdds;
	}
	public double getLoseOdds() {
		return loseOdds;
	}
	public void setLoseOdds(double loseOdds) {
		this.loseOdds = loseOdds;
	}
	
	public String show(){
		StringBuffer sbf=new StringBuffer();
		sbf.append("seq:").append(seq);
		sbf.append(",").append("league:").append(league);
		sbf.append(",").append("date:").append(date);
		sbf.append(",").append("home:").append(home);
		sbf.append(",").append("guest:").append(guest);
		sbf.append(",").append("winOdds:").append(winOdds);
		sbf.append(",").append("eqOdds:").append(eqOdds);
		sbf.append(",").append("loseOdds:").append(loseOdds);
		return sbf.toString();
	}
	@Override
	public String toString() {
		StringBuffer sbf=new StringBuffer();
		sbf.append("game{");
		sbf.append("seq:").append(seq);
		sbf.append(",").append("league:").append(league);
		sbf.append(",").append("date:").append(date);
		sbf.append(",").append("home:").append(home);
		sbf.append(",").append("guest:").append(guest);
		sbf.append(",").append("winOdds:").append(winOdds);
		sbf.append(",").append("eqOdds:").append(eqOdds);
		sbf.append(",").append("loseOdds:").append(loseOdds);
		sbf.append("}");
		return sbf.toString();
	}
	
	private Double returnOdds;
	private Double winPer;
	private Double eqPer;
//	private Double losePer;
	/**根据概率获取结果*/
	public String getResult(){
		if(returnOdds ==null){
			returnOdds=winOdds*eqOdds*loseOdds/(winOdds*eqOdds+winOdds*loseOdds+eqOdds*loseOdds);
			winPer=returnOdds/winOdds;
			eqPer=returnOdds/eqOdds;
//			losePer=returnOdds/loseOdds;
		}
		double result=Math.random();
		if(result<=winPer){
			return "胜";
		}else if(result<=winPer+eqPer){
			return "平";
		}else{
			return "负";
		}
	}
	
	public static void main(String[] args) {
		Game game=new Game();
		game.setWinOdds(5.25);
		game.setEqOdds(3.67);
		game.setLoseOdds(1.67);
		for(int i=0;i<10;i++){
			System.out.println(game.getResult());
		}
		
	}
	
}
