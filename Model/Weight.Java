import java.time.LocalDate;

public class Weight {
	private double weightLbs;
	private LocalDate date;
	
	public Weight( double weightLbs, LocalDate date ){
		this.weightLbs = weightLbs;
		this.date = date;
	}
	
	public Weight( double weightLbs){
		this.weightLbs = weightLbs;
		this.date = LocalDate.now();
	}
	
	public getDate(){
		return this.date;
	}
	public getWeightLbs(){
		return this.weightLbs;
	}
	public double convertWeightToKg( double weightLbs){
		return weightLbs * 0.453592
	}
	public setDate(LocalDate date ){
		this.date = date;
	}
	public setWeightLbs(double weightLbs){
		this.weightLbs = weightLbs;
	}
}