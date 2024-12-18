import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;

    private int width;

    private int height;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        width = picture.width();
        height = picture.height();
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    private Color getCorrectColor(int column, int row) {
        if (row < 0) {
            return picture.get(column, height - 1);
        }
        if (row >= height) {
            return picture.get(column, 0);
        }
        if (column < 0) {
            return picture.get(width - 1, row);
        }
        if (column >= width) {
            return picture.get(0, row);
        }
        return picture.get(column, row);
    }

    // 水平，keep row不变
    private double caculateHorizontal(int column, int row) {
        Color x = getCorrectColor(column - 1, row);
        Color y = getCorrectColor(column + 1, row);
        return Math.pow(x.getRed() - y.getRed(), 2) +
                Math.pow(x.getGreen() - y.getGreen(), 2) +
                Math.pow(x.getBlue() - y.getBlue(), 2);
    }

    // 垂直，keep column不变
    private double caculateVertical(int column, int row) {
        Color x = getCorrectColor(column, row - 1);
        Color y = getCorrectColor(column, row + 1);
        return Math.pow(x.getRed() - y.getRed(), 2) +
                Math.pow(x.getGreen() - y.getGreen(), 2) +
                Math.pow(x.getBlue() - y.getBlue(), 2);
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            throw new IndexOutOfBoundsException();
        }

        double energyOfVertical = caculateVertical(x, y);
        double energyOfHorizontal = caculateHorizontal(x, y);
        return energyOfVertical + energyOfHorizontal;
    }

    /* assum j >= 1 */
    private double finMin(int i, int j) {
        if (i == 0) {
            return Math.min(energy(i, j - 1), energy(i + 1, j - 1));
        }
        if (i == width - 1) {
            return Math.min(energy(i - 1, j - 1), energy(i, j - 1));
        }
        double e = energy(i, j - 1);
        return Math.min(Math.min(e, energy(i - 1, j - 1)), energy(i + 1, j - 1));
    }

    private void findFirstRow(int[] array) {
        int x = 0;
        double e = energy(0, 0);
        for (int i = 1; i < width; i++) {
            if (energy(i, 0) < e) {
                e = energy(i, 0);
                x = i;
            }
        }
        array[0] = x;
    }

    // M(i,j)=e(i,j)+min(M(i−1,j−1),M(i,j−1),M(i+1,j−1))
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] array = new int[height];
        if (height == 1) {
            findFirstRow(array);
            return array;
        }
        int x = 0;
        findFirstRow(array);
        for (int j = 1; j < height; j++) {
            double minEnergy = energy(0, j) + finMin(0, j);
            for (int i = 1; i < width; i++) {
                double currentEnergy = energy(i, j);
                if (currentEnergy < minEnergy) {
                    minEnergy = energy(i, j);
                    x = i;
                }
            }
            array[j] = x;
        }
        return array;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // 创建一个新的图片对象，尺寸是交换过来的
        Picture transposed = new Picture(height, width);

        // 对原图像的每个像素进行遍历并转置
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 获取原图像 (x, y) 的像素颜色
                Color color = picture.get(x, y);
                // 将该像素颜色设置到新图像 (y, x) 的位置
                transposed.set(y, x, color);
            }
        }

        Picture original = picture;
        picture = transposed;
        height = transposed.height();
        width = transposed.width();
        int[] array = findVerticalSeam();

        picture = original;
        transposed = null;
        return array;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeHorizontalSeam(picture, seam);
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height) {
            throw new IllegalArgumentException();
        }
        SeamRemover.removeVerticalSeam(picture, seam);
    }
}
