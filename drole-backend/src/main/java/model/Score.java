package model;

import java.util.Date;

public class Score {
	private int score_id;
	private int profile_score;
	private Date score_date;
	private int user_id;
	
	public Score() {
		this.score_id = 0;
		this.profile_score = 0;
		this.score_date = null;
		this.user_id = 0;
	}
	
	public Score(int score_id, int profile_score, Date score_date, int user_id) {
		this.score_id = score_id;
		this.profile_score = profile_score;
		this.score_date = score_date;
		this.user_id = user_id;
	}

	public int getScore_id() {
		return score_id;
	}

	public void setScore_id(int score_id) {
		this.score_id = score_id;
	}

	public int getProfile_score() {
		return profile_score;
	}

	public void setProfile_score(int profile_score) {
		this.profile_score = profile_score;
	}

	public Date getScore_date() {
		return score_date;
	}

	public void setScore_date(Date score_date) {
		this.score_date = score_date;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public String toString() {
		return "Score [score_id=" + score_id +", profile_score="+profile_score+ ", score_date=" + score_date + ", user_id=" + user_id+"]";
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getScore_id() == ((Score) obj).getScore_id());
	}
}
