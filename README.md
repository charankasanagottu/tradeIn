# TradeIn - Cryptocurrency Trading Application

## Overview

**TradeIn** is a feature-rich cryptocurrency trading platform designed
for a seamless user experience in the fast-paced world of digital
currency. It offers real-time market insights, interactive charts, and
secure transactions, setting it apart from other trading apps. This
application integrates APIs for real-time data, ensures secure payments,
and provides an intuitive interface for managing cryptocurrency
portfolios.

------------------------------------------------------------------------

## Features

-   **Real-Time Market Data**: Integrated CoinGecko API to display live
    cryptocurrency prices and market trends.
-   **Secure Transactions**: Utilized Razorpay and Stripe APIs for safe
    and secure payment processing.
-   **AI-Powered Chatbot**: Leveraged Gemini API for providing real-time
    market analysis and user support.
-   **Portfolio Management**: Comprehensive tools for buy/sell orders,
    asset tracking, and watchlist management.
-   **Responsive Design**: Designed for mobile and web using React and
    Tailwind CSS to ensure a smooth experience across all devices.

------------------------------------------------------------------------

## Front-End Services

-   **User Interface**: Developed the front-end using **React** to
    create an intuitive and responsive UI.
-   **State Management**: Implemented **React Hooks** for dynamic user
    interactions and real-time updates.
-   **API Integration**: Integrated **CoinGecko API** to display
    real-time cryptocurrency market data directly in the application.
-   **Interactive Components**: Created components for user
    authentication, watchlist management, and wallet services, ensuring
    seamless navigation.
-   **Buy/Sell Orders**: Handled buy/sell transactions and portfolio
    tracking by executing efficient API calls to the backend services.
-   **Responsive Design**: Used **Tailwind CSS** to ensure a smooth,
    responsive experience on both desktop and mobile devices.
-   **API Communication**: Utilized **Axios** for efficient REST API
    communication, ensuring smooth data flow between front-end and
    back-end.
-   **Form Validation**: Implemented secure and user-friendly form
    validation for registration and login processes.

------------------------------------------------------------------------

## Back-End Services

-   **Robust Backend**: Built using **Java Spring Boot** for scalable
    and high-performance operations.
-   **RESTful APIs**: Developed comprehensive **RESTful APIs** to handle
    user authentication, wallet management, and payment processing.
-   **Real-Time Data**: Integrated **CoinGecko API** for fetching
    real-time market data and ensuring up-to-date user information.
-   **Secure Payments**: Integrated **Razorpay** and **Stripe** for
    handling secure transactions during buy/sell operations.
-   **Cryptocurrency Management**: Managed user cryptocurrency balances,
    buy/sell orders, and asset tracking using a **microservices
    architecture**.
-   **Password Recovery**: Implemented a secure password recovery
    process via **SMTP-based email notifications**.
-   **Database Management**: Utilized **MySQL** for managing all user
    data, transactions, and orders securely and efficiently.

------------------------------------------------------------------------

## Technologies Used

### Front-End:

-   **React**: For building an intuitive user interface and managing
    dynamic user interactions.
-   **Tailwind CSS**: For designing responsive and modern user
    interfaces.
-   **Axios**: For handling HTTP requests to the backend.
-   **CoinGecko API**: For fetching real-time cryptocurrency market
    data.

### Back-End:

-   **Java Spring Boot**: For developing scalable backend services and
    RESTful APIs.
-   **MySQL**: For storing and managing user, transaction, and order
    data.
-   **Razorpay & Stripe**: For handling secure payments.
-   **SMTP**: For sending secure email notifications for password
    recovery.

### Tools:

-   **Postman**: Used for testing and debugging APIs.
-   **Git**: Version control for tracking changes and collaborating
    efficiently.

------------------------------------------------------------------------

## How to Run

### Front-End

1.  Navigate to the `frontend` directory.
2.  Install dependencies: 
    ``` bash 
    npm install
    ```

## Back-End

1.  Navigate to the `backend` directory.

2.  Install dependencies and configure MySQL:

    ``` bash
    mvn clean install
    ```

3.  Run the Spring Boot application:

    ``` bash
    mvn spring-boot:run
    ```

------------------------------------------------------------------------

Ensure that MySQL is running and properly configured for the backend
services to connect.
