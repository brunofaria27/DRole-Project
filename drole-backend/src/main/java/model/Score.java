package model;


public class Score {

	private int score_id;
	private boolean rate;
	private int valued_id;
	private int valuer_id;

	public Score(int score_id, boolean rate, int valued_id, int valuer_id) {
		this.score_id = score_id;
		this.rate = rate;
		this.valued_id = valued_id;
		this.valuer_id = valuer_id;
	}
	
	public Score(boolean rate, int valued_id, int valuer_id) {
		this.rate = rate;
		this.valued_id = valued_id;
		this.valuer_id = valuer_id;
	}
	
	public Score() {
		this.rate = false;
		this.valued_id = 0;
		this.valuer_id = 0;
	}

	public int getScore_id() {
		return score_id;
	}

	public void setScore_id(int score_id) {
		this.score_id = score_id;
	}

	public boolean isRate() {
		return rate;
	}

	public void setScore(boolean rate) {
		this.rate = rate;
	}

	public int getValued_id() {
		return valued_id;
	}

	public void setValued_id(int valued_id) {
		this.valued_id = valued_id;
	}

	public int getValuer_id() {
		return valuer_id;
	}

	public void setValuer_id(int valuer_id) {
		this.valuer_id = valuer_id;
	}

	@Override
	public String toString() {
		return "Score [score_id=" + score_id + ", rate=" + rate + ", valued_id=" + valued_id
				+ ", valuer_id=" + valuer_id + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return (this.getScore_id() == ((Score) obj).getScore_id());
	}
}
