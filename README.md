# OrderManagementSystem

**Setting up and running the application locally**:
1. Clone the repository containing the Order Management System application.
2. Make sure you have Java 8 or higher installed on your system.
3. Build the project using Gradle.
4. Run the application as a Spring Boot application.

**API Documentation:**
* Create Order:
  * Endpoint: POST /orders
  * Description: Creates a new order.
  * Request Body: JSON representing the order details.
  * Example Request:
  `{
  "userId": "1",
  "deliveryAddress": "Abc123",
  "orderDate": "2024-04-28T10:15:30",
  "items": {"item1":100,"item2":200,"item3":300},
  "totalAmount": 23454
  }`
  * Response: Returns the created order.
  * Example Response:
  `{
  "id": "e877d381-b191-4b09-a74c-2e1c4b499d61",
  "userId": "1",
  "deliveryAddress": "Abc123",
  "orderDate": "2024-04-28T10:15:30",
  "items": {
  "item1": 100,
  "item2": 200,
  "item3": 300
  },
  "status": "PENDING",
  "totalAmount": 23454.0
  }`
* Get Order Details:
  * Endpoint: GET /orders/{orderId}
  * Description: Retrieves details of a specific order.
  * Path Variable: orderId (ID of the order to retrieve).
  * Example Request:
    `GET /orders/123456`
  * Response: Returns the details of the requested order if found, otherwise returns a 404 Not Found.
  * Example Response:
    `{
      "id": "e877d381-b191-4b09-a74c-2e1c4b499d61",
      "userId": "1",
      "deliveryAddress": "Abc123",
      "orderDate": "2024-04-28T10:15:30",
      "items": {
      "item1": 100,
      "item2": 200,
      "item3": 300
      },
      "status": "PENDING",
      "totalAmount": 23454.0
      }`
* Update Order Status
  * Endpoint: PUT /orders/{orderId}
  * Description: Updates the status of a specific order.
  * Path Variable: orderId (ID of the order to update).
  * Request Parameter: status (New status value).
  * Example Request:
    `PUT /orders/e877d381-b191-4b09-a74c-2e1c4b499d61?status=COMPLETED`
  * Response: Returns the updated order if found, otherwise returns a 404 Not Found.
  * Example Response:
    `{
      "id": "e877d381-b191-4b09-a74c-2e1c4b499d61",
      "userId": "1",
      "deliveryAddress": "Abc123",
      "orderDate": "2024-04-28T10:15:30",
      "items": {
      "item1": 100,
      "item2": 200,
      "item3": 300
      },
      "status": "COMPLETED",
      "totalAmount": 23454.0
      }`
* Cancel Order
  * Endpoint: PUT /orders/cancelOrder/{orderId}
  * Description: Cancels a specific order.
  * Path Variable: orderId (ID of the order to cancel).
  * Example Request:
    `PUT /orders/cancelOrder/e877d381-b191-4b09-a74c-2e1c4b499d61`
  * Response: Returns the canceled order if found, otherwise returns a 404 Not Found.
  * Example Response:
      `{
      "id": "e877d381-b191-4b09-a74c-2e1c4b499d61",
      "userId": "1",
      "deliveryAddress": "Abc123",
      "orderDate": "2024-04-28T10:15:30",
      "items": {
      "item1": 100,
      "item2": 200,
      "item3": 300
      },
      "status": "CANCELLED",
      "totalAmount": 23454.0
      }`

**Concurrency Considerations**

The application leverages Spring's @Async annotation to perform asynchronous execution of certain operations, such as creating, updating, and canceling orders. This allows the application to handle multiple requests concurrently without blocking the main thread.

To ensure thread safety, the application uses ConcurrentHashMap to store locks associated with each order ID. This prevents concurrent access to the same order during updates or cancellations. Additionally, ReentrantLock is used to acquire and release locks in a thread-safe manner.

**Concurrency Model and Mechanisms**

* Asynchronous Execution: The application utilizes Spring's asynchronous processing capabilities (@EnableAsync) to execute certain operations asynchronously, thereby improving performance and scalability by utilizing non-blocking I/O operations.
* Concurrent Hash Map: A ConcurrentHashMap is used to store locks associated with each order ID. This concurrent data structure ensures thread safety during concurrent access to order resources.
* Reentrant Locks: A ReentrantLock is employed to provide exclusive access to critical sections of code when updating or canceling orders. This ensures that only one thread can modify an order's status at a time, preventing race conditions and ensuring data integrity.

By combining these concurrency mechanisms, the application achieves efficient and thread-safe execution of order management operations even under high concurrency scenarios.