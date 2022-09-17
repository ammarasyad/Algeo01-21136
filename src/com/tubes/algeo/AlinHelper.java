package com.tubes.algeo;

/**
 * Helper class to reference both MatrixOps and GaussianElimination in a single class.
 */
public final class AlinHelper {
    private static final MatrixOps ops = MatrixOps.getInstance();
    private static final GaussianElimination elim = GaussianElimination.getInstance();

    private static AlinHelper INSTANCE = null;

    private AlinHelper() { }

    public static AlinHelper getInstance() {
        if (INSTANCE == null) INSTANCE = new AlinHelper();
        return INSTANCE;
    }

    public MatrixOps getOps() {
        return ops;
    }

    public GaussianElimination getElim() {
        return elim;
    }
}
