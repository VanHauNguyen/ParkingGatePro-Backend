# ParkingGatePro – Backend (Spring Boot)

本專案為 ParkingGatePro 停車場管理系統之後端服務，  
負責處理車輛入場 / 出場邏輯、資料儲存與費用計算。

前端為 iOS App（ParkingGatePro-iOS），  
採用前後端分離架構，透過 RESTful API 溝通。

---

## 核心功能
- 車輛管理（Vehicle）
- 入場 / 出場流程（Parking IN / OUT）
- 停車場事件紀錄（ParkingEvent）
- 停車 Session 管理（ParkingSession）
- 月租車判斷（MonthlySubscription）
- 停車費用計算（FeeCalculatorService）
- 最近活動查詢 API

---

## 技術架構
- Java 21
- Spring Boot
- Spring Data JPA
- MariaDB / MySQL
- RESTful API
- 分層架構：
  - Controller
  - Service
  - Repository
  - Entity / DTO

---

## 專案結構




src/main/java/com/parking/parkingsystem
├─ controller
├─ service
├─ repository
├─ entity
└─ dto





---

## 執行方式
1. 安裝 Java 21 + Maven
2. 建立資料庫（MariaDB / MySQL）
3. 設定 `application.properties`
4. 執行：


./mvnw spring-boot:run




---

## 執行方式
1. 安裝 Java 21 + Maven
2. 建立資料庫（MariaDB / MySQL）
3. 設定 `application.properties`
4. 執行：

```bash
./mvnw spring-boot:run


Server 預設啟動於：
http://localhost:8080


本專案為課堂期末實作，重點在於：

將 UML 設計轉化為實際後端架構

實作完整停車業務流程（IN / OUT / 月租 / 計費）

與 iOS App 進行前後端整合




