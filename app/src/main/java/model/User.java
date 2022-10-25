package model;

public class User {
    private int id;
    private String email;
    private String password;
    private String ten;
    private String diaChi;
    private int namSinh;
    private String hinhKhachHang;

    public User(int id, String email, String password, String ten, String diaChi, int namSinh,String hinhKhachHang) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.ten = ten;
        this.diaChi = diaChi;
        this.namSinh = namSinh;
        this.hinhKhachHang = hinhKhachHang;
    }

    public String getHinhKhachHang() {
        return hinhKhachHang;
    }

    public void setHinhKhachHang(String hinhKhachHang) {
        this.hinhKhachHang = hinhKhachHang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int  NamSinh) {
        this.namSinh = namSinh;
    }
}
