<H2>ERD</H2>
<a href="https://www.erdcloud.com/d/AjyWJ8i6PkcZcs6bn">
  <img width="1054" height="630" alt="DeliveryERD" src="https://github.com/user-attachments/assets/dcaf2f1c-6282-430b-a571-8f9062d2e334" />
</a>

<H2>테이블 명세서</H2>

#### User

| 컬럼 | 타입 | 제약 | 설명 |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, 자동 증가 | 사용자 번호 |
| `username` | VARCHAR(50) | NOT NULL | 로그인 아이디 |
| `password` | VARCHAR(255) | NOT NULL | 암호화된 비밀번호 |
| `role` | VARCHAR(20) | NOT NULL | 사용자 권한 |
| `created_at` | TIMESTAMP | NOT NULL | 사용자 생성 시각 |
| `updated_at` | TIMESTAMP | NOT NULL | 사용자 수정 시각 |

#### Menu

| 컬럼 | 타입 | 제약 | 설명 |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, 자동 증가 | 메뉴 번호 |
| `user_id` | BIGINT | FK → `users(id)`, NOT NULL | 메뉴를 등록한 사용자 |
| `menu_name` | VARCHAR(100) | NOT NULL | 메뉴 이름 |
| `menu_price` | INT | NOT NULL | 메뉴 가격 |
| `menu_description` | VARCHAR(255) | NULL | 메뉴 설명 |
| `is_delete` | BOOLEAN | NOT NULL, DEFAULT FALSE | 메뉴 삭제 여부 |
| `created_at` | TIMESTAMP | NOT NULL | 메뉴 생성 시각 |
| `updated_at` | TIMESTAMP | NOT NULL | 메뉴 수정 시각 |

#### Order

| 컬럼 | 타입 | 제약 | 설명 |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, 자동 증가 | 주문 번호 |
| `user_id` | BIGINT | FK → `users(id)`, NOT NULL | 주문한 사용자 |
| `menu_id` | BIGINT | FK → `menus(id)`, NOT NULL | 주문한 메뉴 |
| `quantity` | INT | NOT NULL | 주문 수량 |
| `total_price` | INT | NOT NULL | 총 주문 금액 |
| `delivery_address` | VARCHAR(255) | NOT NULL | 배달 주소 |
| `order_status` | VARCHAR(20) | NOT NULL | 주문 상태 |
| `created_at` | TIMESTAMP | NOT NULL | 주문 생성 시각 |
| `updated_at` | TIMESTAMP | NOT NULL | 주문 수정 시각 |

#### Payment

| 컬럼 | 타입 | 제약 | 설명 |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, 자동 증가 | 결제 번호 |
| `order_id` | BIGINT | FK → `orders(id)`, NOT NULL | 결제 대상 주문 |
| `payment_sum` | INT | NOT NULL | 결제 금액 |
| `payment_option` | VARCHAR(30) | NOT NULL | 결제 수단 |
| `payment_status` | VARCHAR(20) | NOT NULL | 결제 상태 |
| `created_at` | TIMESTAMP | NOT NULL | 결제 생성 시각 |
| `updated_at` | TIMESTAMP | NOT NULL | 결제 수정 시각 |
