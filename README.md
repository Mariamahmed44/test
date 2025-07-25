
# 🧪 Advantage E-Commerce Automation

Automated UI testing suite for [Advantage Online Shopping](https://advantageonlineshopping.com/#/), built with Java, Selenium WebDriver, TestNG, and ExtentReports. This project covers key user workflows such as registration, product filtering, cart operations, and purchase completion.

---

## 🚀 Features Covered

- User registration and login
- Product filtering and category navigation
- Add-to-cart from both category and home sections
- Cart item editing and deletion
- End-to-end purchase flow
- Validation of product details from "Popular Items"
- HTML test reporting with ExtentReports

---

## 🧰 Tech Stack

- **Language**: Java  
- **Automation**: Selenium WebDriver  
- **Test Framework**: TestNG  
- **Build Tool**: Maven  
- **Driver Management**: WebDriverManager  
- **Reporting**: ExtentReports (HTML format)  
- **CI/CD**: GitHub Actions  

---

## 📂 Project Structure

```
project-root/
├── pom.xml                      # Maven dependencies
├── testng.xml                   # TestNG suite configuration
├── .gitignore                   # Version control filter
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/           # Page Object Model (POM) classes
│   │       
│   └── test/
│       └── java/
│           ├── setup/            # New folder for base classes, setup, hooks
│           ├── tests/           # Test classes
│           └── utils/reports/   # ExtentReports output (HTML)
```

---

## ⚙️ Prerequisites

- Java 17+
- Maven 3.9+
- Google Chrome browser
- Internet connection (for WebDriverManager to download drivers)

---

## ▶️ How to Run Tests

### ✅ Run all tests defined in `testng.xml`:
```bash
mvn test
```

### ✅ Run a specific test class:
```bash
mvn -Dtest=EditAndDeleteItemsTest test
```

> ℹ️ All tests run using Chrome only by default.

---

## 📊 Test Reports

After test execution, a detailed HTML report is generated.

📁 Location:  
```
src/test/java/utils/reports/UI_test_report.html
```

📖 To view:
- Open the file in your browser manually.

---

## 🧑 Authors

**Ahmed**  
[GitHub Profile](https://github.com/Ahmedkh285) 

**Mariam**  
[GitHub Profile](https://github.com/Mariamahmed44) 

**Sherif**  
[GitHub Profile](https://github.com/Sherriif) 

**Mohamed**  
[GitHub Profile](https://github.com/MOHAMEDRAGEB2020) 

**Omar**  
[GitHub Profile](https://github.com/omargamal1997) 

**Joman**  
[GitHub Profile](https://github.com/jomankhattab) 

**Habeba**  
[GitHub Profile](https://github.com/habebahamy208) 

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
