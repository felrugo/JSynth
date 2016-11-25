package main;

public class Note implements Comparable {
	static double[] nullfreqs = { 16.35, 17.32, 18.35, 19.45, 20.60, 21.83, 23.12, 24.50, 25.96, 27.50, 29.14, 30.87 };
double freq;
double elst;
double phase;
int sc;
String name;
int oct;

	Note(String name, int oct)
	{
		this.name = name;
		this.oct = oct;
	switch(name)
	{
	case "C":
		freq = nullfreqs[0];
		break;
	case "C#":
		freq = nullfreqs[1];
		break;
	case "D":
		freq = nullfreqs[2];
		break;
	case "D#":
		freq = nullfreqs[3];
		break;
	case "E":
		freq = nullfreqs[4];
		break;
	case "F":
		freq = nullfreqs[5];
		break;
	case "F#":
		freq = nullfreqs[6];
		break;
	case "G":
		freq = nullfreqs[7];
		break;
	case "G#":
		freq = nullfreqs[8];
		break;
	case "A":
		freq = nullfreqs[9];
		break;
	case "Bb":
		freq = nullfreqs[10];
		break;
	case "B":
		freq = nullfreqs[11];
		break;
	default:
		freq = 0.0;
		break;
	}
	for(int i = 0; i < oct; i++)
		freq *= 2;
	}
	
	public boolean equals(Object o)
	{
		Note on = (Note) o;
		return this.name.equals(on.name) && this.oct == on.oct;
	}
	
	double getFreq()
	{
		return freq;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Note on = (Note) o;
		if(this.name.equals(on.name) && this.oct == on.oct)
			return 0;
		if(this.freq > on.freq)
			return 1;
		return -1;
	}

}
