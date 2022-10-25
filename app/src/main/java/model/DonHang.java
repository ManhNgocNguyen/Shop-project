package model;public class DonHang {
    private int id_don;
    private String taiKhoanKhachHang;
    private String tenKhachHang;
    private String diaChi;
    private int idSanPham;
    private String hinhAnhSanPham;
    private String moTaSanPham;
    private int soLuong;

    public DonHang(int id_don, String taiKhoanKhachHang, String tenKhachHang, String diaChi, int idSanPham, String hinhAnhSanPham, String moTaSanPham, int soLuong) {
        this.id_don = id_don;
        this.taiKhoanKhachHang = taiKhoanKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.diaChi = diaChi;
        this.idSanPham = idSanPham;
        this.hinhAnhSanPham = hinhAnhSanPham;
        this.moTaSanPham = moTaSanPham;
        this.soLuong = soLuong;
    }

    public int getId_don() {
        return id_don;
    }

    public void setId_don(int id_don) {
        this.id_don = id_don;
    }

    public String getTaiKhoanKhachHang() {
        return taiKhoanKhachHang;
    }

    public void setTaiKhoanKhachHang(String taiKhoanKhachHang) {
        this.taiKhoanKhachHang = taiKhoanKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getHinhAnhSanPham() {
        return hinhAnhSanPham;
    }

    public void setHinhAnhSanPham(String hinhAnhSanPham) {
        this.hinhAnhSanPham = hinhAnhSanPham;
    }

    public String getMoTaSanPham() {
        return moTaSanPham;
    }

    public void setMoTaSanPham(String moTaSanPham) {
        this.moTaSanPham = moTaSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
