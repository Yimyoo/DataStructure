public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    private static final double CONCERT_0 = CONCERT_A * Math.pow(2, -24.0 / 12.0);
    private static final double CONCERT_36 = CONCERT_A * Math.pow(2, 12.0 / 12.0);


    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        synthesizer.GuitarString stringA = new synthesizer.GuitarString(CONCERT_A);
        stringA.pluck();
        synthesizer.GuitarString stringC = new synthesizer.GuitarString(CONCERT_C);
        stringC.pluck();
        synthesizer.GuitarString string0 = new synthesizer.GuitarString(CONCERT_0);
        string0.pluck();
        synthesizer.GuitarString string36 = new synthesizer.GuitarString(CONCERT_36);
        string36.pluck();

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'a') {
                    stringA.pluck();
                } else if (key == 'c') {
                    stringC.pluck();
                } else if (key == 'q') {
                    string0.pluck();
                } else if (key == ' ') {
                    string36.pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = stringA.sample() + stringC.sample() + string0.sample() + string36.sample();

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            stringA.tic();
            stringC.tic();
            string0.tic();
            string36.tic();
        }
    }
}
