package model;

public class LoaiSanPham {
    private int id;
    private String tenSanPham;
    private String hinhSanPham;

    public LoaiSanPham(int id, String tenSanPham, String hinhSanPham) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.hinhSanPham = hinhSanPham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getHinhSanPham() {
        return hinhSanPham;
    }

    public void setHinhSanPham(String hinhSanPham) {
        this.hinhSanPham = hinhSanPham;
    }
}
