# PROMPT DỰ ÁN — SPRUCE AUTO FARM HUGO SMP

Bạn là lập trình viên Minecraft Fabric cấp cao. Hãy phát triển dự án Java tên `SpruceAutoFarm` cho Minecraft Java 1.21.11, Fabric, Java 21. Mục tiêu là tạo một client mod có mã nguồn đầy đủ, có thể build thành `SpruceAutoFarm.jar`.

## Nguyên tắc bắt buộc
1. Chỉ dùng API Minecraft/Fabric công khai và thao tác client hợp lệ.
2. Không bypass anti-cheat, không giả mạo packet, không khai thác lỗi server, không né giới hạn hoặc cơ chế bảo vệ.
3. Trước khi bật macro/tự động tương tác trên Hugo SMP, yêu cầu người dùng xác nhận server cho phép. Nếu server cấm bot/macro, chỉ cung cấp chế độ hỗ trợ hiển thị và thao tác thủ công.
4. Không tuyên bố tính năng đã hoạt động nếu chưa viết code, compile và kiểm thử.
5. Không tạo code giả, TODO được gọi là hoàn thành, hoặc API tự bịa. Nếu API không chắc chắn, kiểm tra Fabric API/Minecraft 1.21.11 docs và giải thích.
6. Giữ các chức năng Auto Farm và Auto Order độc lập, có công tắc riêng và trạng thái rõ ràng.

## Cấu hình dự án
- Minecraft: 1.21.11
- Loader: Fabric
- Java: 21
- Mod ID: `spruceautofarm`
- Tên hiển thị: `Spruce Auto Farm`
- Artifact: `SpruceAutoFarm.jar`
- Dùng Fabric Loom phù hợp với 1.21.11, Fabric Loader và Fabric API đúng phiên bản.
- Ưu tiên Mojang mappings hoặc Yarn tương thích chính xác; không trộn tên mapping.

## Tính năng Auto Farm
Thiết kế theo state machine có trạng thái HUD:
- SCANNING: tìm khu vực trồng hợp lệ.
- CHECKING_SUPPLIES: kiểm tra Spruce Sapling và Bone Meal.
- PLANTING: hỗ trợ trồng theo bố cục 2×2.
- GROWING: dùng Bone Meal khi điều kiện hợp lệ.
- CHOPPING: nhận diện và xử lý cây Spruce.
- INVENTORY: báo túi đồ đầy và áp dụng tùy chọn dọn túi an toàn.
- PAUSED / ERROR: dừng khi mất mục tiêu, thiếu vật phẩm, GUI mở, người chơi bị di chuyển hoặc điều kiện không còn hợp lệ.

Yêu cầu:
- Không phá block ngoài cây Spruce được xác định.
- Không thao tác nếu người chơi đang mở GUI, chat, nhận damage hoặc không có quyền/điều kiện phù hợp.
- Có giới hạn thời gian, khoảng cách và số lần thử; có nút dừng khẩn cấp.
- Không tự di chuyển ra khỏi khu farm hoặc thực hiện hành động có thể gây rơi/chết.
- Tất cả hành động phải dùng cơ chế client thông thường, không gửi packet tùy chỉnh để lách server.

## Tính năng Auto Order
Tách thành module riêng, mặc định OFF:
- Có giao diện cấu hình mục tiêu order Spruce Log, Stick và tài khoản nhận Spruce Sapling.
- Chỉ phân tích thông tin order đang hiển thị hợp lệ; không giả mạo dữ liệu.
- Có chế độ preview: hiển thị item, số lượng, giá và hành động dự kiến, yêu cầu người chơi xác nhận.
- Chỉ tự thao tác GUI nếu người dùng xác nhận Hugo SMP cho phép macro và giao diện có thể được điều khiển bằng thao tác client hợp lệ.
- Không tự xác nhận giao dịch không thể hoàn tác nếu chưa có xác nhận rõ ràng.
- Không tự đăng nhập, không xử lý mật khẩu/token, không tự động giao dịch khi không có người dùng giám sát.
- Dừng nếu GUI thay đổi bất ngờ, dữ liệu không khớp hoặc order không còn tồn tại.

## Giao diện
- HUD gọn ở góc trái, có thể đổi vị trí và ẩn/hiện.
- Hiển thị: Farm ON/OFF, Order ON/OFF, state, số Sapling, Bone Meal, Spruce Log, tiến trình và thông báo lỗi.
- Màn hình Settings có checkbox/toggle, slider tốc độ thao tác hợp lệ, cấu hình phím.
- Phím mặc định: K bật/tắt Farm, J bật/tắt Order, P tạm dừng khẩn cấp; cho phép đổi phím.
- Lưu cấu hình vào file JSON trong thư mục config.
- Ngôn ngữ giao diện tiếng Việt và tiếng Anh.

## Chất lượng code
- Chia package: config, farm, order, hud, input, util.
- Dùng state machine, không dùng vòng lặp blocking.
- Không làm treo client thread.
- Có logging và xử lý null/exception.
- Tránh phụ thuộc không cần thiết.
- Viết unit test cho logic thuần Java nếu phù hợp.
- Kiểm tra tương thích chính xác Minecraft 1.21.11.

## Quy trình làm việc bắt buộc
1. Đọc toàn bộ source hiện có trước khi sửa.
2. Lập danh sách file cần tạo/sửa.
3. Triển khai theo từng giai đoạn: build nền → HUD/keybind/config → nhận diện cây → farm state machine → order preview → tính năng được server cho phép.
4. Sau mỗi giai đoạn, chạy `gradle build`; sửa hết lỗi compile.
5. Không xóa tính năng cũ khi thêm tính năng mới.
6. Cuối cùng cung cấp toàn bộ source, lệnh build, đường dẫn JAR và danh sách tính năng đã kiểm thử/chưa kiểm thử.
7. Nếu không thể chạy build hoặc vào server test, nói rõ giới hạn; không được giả vờ đã kiểm thử.

Bắt đầu bằng cách kiểm tra project hiện tại, xác nhận phiên bản dependency cho Minecraft 1.21.11, sau đó triển khai giai đoạn 1.
