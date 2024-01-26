import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import page.GetImages;
import utilities.driver.InitWebDriver;

import java.util.List;

public class TestDemo {
    @Test
        //"Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng","Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình","Ninh Thuận",
    void demo() {
        List<String> part1 = List.of("Công ty Sản xuất phân bón tỉnh ", "Công ty Sản xuất hàng tiêu dùng tỉnh ");
        List<String> part2 = List.of("Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thừa Thiên - Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái");
        WebDriver driver = new InitWebDriver().getDriver("chrome", true);
        for (String p1 : part1) {
            for (String p2 : part2) {
                new GetImages(driver).navigateToGoogleMaps().searchAndSelectFirstResult("%s%s".formatted(p1, p2)).getInformation();
            }
        }
        driver.quit();

    }
}
