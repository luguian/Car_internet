package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.lu.car_internet.R;


public class DetailActivity extends Activity {

	private TextView tvContent;
	String str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		tvContent = (TextView) findViewById(R.id.tv_detail_content);
		Intent intent = getIntent();
		int item = intent.getIntExtra("item", 0);
		switch (item) {
		// 范围在[201,207]内的是科目二点击条目后跳转的详情
		case 201:
			str = "<p>一、考前准备</p>\n<p><br>1垫子：这个用途不用说了吧，建议最好选用大一些的垫子，我的经验，太小的垫子容易滑出去，万一在做入库或者侧方的时候，垫子滑出去，就会麻烦了。</br><br>2凤尾夹：用于夹住安全带。</br><br>3扑克牌或者餐巾纸：万一考试车左侧转向灯损坏或丢失，用于填坑(可不带，据我观察，至少前山考场，绝大多数车都有这个灯的)。</br><br>4记号笔：将自己考试车对应的库(侧方库、倒车库)写在手背上，这样比去看准考证来得快，避免出现入错库的问题。</br><br>5粉笔：在考试车右前门上做记号，以便半坡启动对点。</br></p><p>二、待考区</p> \n<p><br>1早去厕所：进入候考大厅等待时，在身份证交上去之后，尽量立刻去厕所，一个是别在候考大厅傻乎乎的看事故视频，免得更紧张。另外一个，厕所外面的彩钢板围墙上有不少的洞眼，可以看到半坡的一些情况，如考试车道情况、监控杆、感应钉。最后，早去厕所可以避免到通知考试的时候人不在，急匆匆赶回来，加剧心理紧张。</br><br>2分散注意力：不可避免的，还是需要在候考大厅等候，这时候，可以尽量找人聊天说话，或者干脆闭目养神。因为这时候手机都关机了，也不能玩。正前方的电视都是事故视频，看着难受，所以别傻乎乎的盯着电视，也别一直跑到窗户边去看人家半坡，这些都是会加重紧张感的。</br><br>3准备入场：一般驾校都是让学员排队等在入口处，这时候最好别排第一位，因为第一位会看到很多人垂头丧气的过来，也会听到考场里教练斥责没过的学员，会加重紧张感。个人建议排在5、6位，这样即听得到教练叫名字，也不至于还没进场就直接看到失败的场景。</br><br>4领号入场：教练叫名字了，把准考证拿过去，教练会在准考证上写上几道几号车，有的教练还会写上几号库。这时候先别忙进去，仔细看看库和车是不是一致，因为教练写错库的情况也是有的，核对一下花不了几秒钟的。</p></br><p>三、考试准备</p>\n<p><br>1情况观察：进入考场了，可以先远眺几秒钟，大致看看半坡，别的在这个角度看不大到的，然后就可以去自己考试道的起点处准备接车了。这里注意下，不一定先进去的先拿到车，有可能后进来的还先拿到车，大家心态放平，我今天进去后，就等了好一会，比我迟进去的两个人都上车了，我的车都还没到，我正好四处张望下。对了，这时候可以顺便用记号笔把要进的库号写在手上了。</br><br>2接车：车到了，远远的先可以看看外观，一般来说就看看左车灯，我还特意看了看轮胎，传说中有的车轮胎气不够饱，平路都要加油门，这个我今天没看到。过去了别急上车，先问问刚下车的学员车况，主要问问离合怎么样，别的也来不及问，一般人都会说的，碰上个别没过而情绪不好的学员，那就算了，别往心里去，反正上车了也知道。</br><br>3上车准备：没上车时，先塞扑克或餐巾纸(左车 灯损坏的情况下)。上车后先不忙系安全带，先坐下，试试点，把座位、后视镜都调整到平常在驾校学车的习惯位置，然后用粉笔在右车门半坡定点停车的习惯位置上画个圈圈，具体哪里就不用多说了吧，驾校肯定都教过。这时候再系上安全带，扯的长一点，用凤尾夹夹住，然后脑袋伸出左边试试看，不够就再扯点，一定要让自己脑袋可以自如的伸出窗外。</br><br>4准备起步：先不放手刹，档位也放在空挡上，点一点油门，看看油门的软硬。然后放下手刹，挂一档，慢慢抬离合，找一找半联动的点同时也试一下离合的软硬。最后呢，车子慢慢动了，把方向盘回直，放开双手，看看车子是不是走直线，若有跑偏，记住跑偏的方向，到时候纠正方向盘用得着的。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 202:
			str = "<p>一、考试目的</p>\n<p><br>1在规定场地内驾驶机动车完成考试项目的情况;</br><br>2对机动车驾驶技能掌握的情况;</br><br>3对机动车空间位置判断的能力。</br></p><p>二、考试项目</p>\n<p><br>倒车入库、坡道起步和定点停车、侧方停车、曲线行驶、直角转弯五项必考。考试细则调整更加严格一些倒桩考试：倒库过程中不能停车 一次倒入 右侧起点倒入车库 然后开到左边倒入车库 开到起点 取消移库侧方停车：操作需一次倒入 中间不可停车直角转弯：不能压线 需一次完成不能停车定点停车坡道起步：停车原规定30公分 现10公分左右 坡道起步按原有正常操作大型客车、牵引车、城市公交车、中型客车、大型货车准驾车型考试项目不得少于6项。大型客车、城市公交车必考项目：桩考、坡道定点停车和起步、直角转弯、通过单边桥、通过连续障碍;牵引车准驾车型必考项目：桩考、坡道定点停车和起步、曲线行驶、直角转弯、限速通过限宽门;中型客车、大型货车准驾车型必考项目：桩考、坡道定点停车和起步、侧方停车、通过单边桥、通过连续障碍。其他考试项目随机选取。小型汽车、小型自动挡汽车、低速载货汽车、普通三轮摩托车、普通二轮摩托车准驾车型考试项目不得少于4项。小型汽车、低速载货汽车必考项目：桩考、坡道定点停车和起步、侧方停车;小型自动挡汽车必考项目：桩考、侧方停车;普通三轮摩托车、普通二轮摩托车准驾车型必考项目：桩考、坡道定点停车和起步、通过单边 桥。其他考试项目随机选取。应当先进行桩考。桩考未出现扣分情形的，补考或者重新预约考试时可以不再进行桩考。</br><br>其他准驾车型的考试项目，由省级公安机关交通管理部门确定。</br></p><p>三、合格标准</p>\n<p><br>满分为100分，设定不合格、减20分、减10分、减5分的项目评判标准。符合下列规定的，考试合格：</br><br>1报考大型客车、牵引车、城市公交车、中型客车、大型货车准驾车型，成绩达到90分的;</br><br>2报考其他准驾车型成绩达到80分的。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 203:
			str = "<p>操作要求</p>\n<p><br>坡道的坡度必须大于等于10度，考C1驾照的陡坡长度要大于等于20，在考试过程中，学员在陡坡停车后，必须在30秒内起步，否则就算失败</br></p><p>操作步骤</p>\n<p><br>1轻点油门，慢松离合器;</br><br>2车身抖动，松手刹同时略加油;</br><br>3一经起步慢松离合。</br></p><p>操作技巧</p><p><br>1挂一档，打右转向灯，车体向右靠，观察右后视镜。当车体右侧距离6号线(如上图)30CM以内时，会正方向轻踩油门保持在1500转直线行驶。</br><br>2观察左后视镜，当1线和左后视镜下沿与眼睛成一线时，踩下离合，放下油门，当3线中心和左后视镜下沿与眼睛成一条线时，踩刹车，拉紧停车驻动器，停车到位。</br><br>3打左转向灯，慢抬离合，当车身有抖动的感觉时或发动机声音变沉闷，左脚保持不动，左脚微松离合器，使转速降至1500转/分时，慢放手刹，车即启动向前行进，坡道起步完成。</br><br>4等车完全上坡之后松开油门，关闭转向灯，顺利下坡，操作完成。</br></p><p>特别注意</p>\n<p>1上坡前做到三步。上坡道看车头右1/3点对准边线，车体回正，保持方向直线行驶。车体距离边线30CM以内，当观后镜根部与停车线上沿成一线时停车，保险杠正好在停车线上。2慢抬离合，当车身有抖动感觉时或发动机声音变沉闷，左脚停住不动，右脚轻踩油门，转速接近2000转/分时，右脚不动，左脚微松离合器，使转速降至1500转/分时，慢放手刹，车即向前行进。3坡道千万不能踩离合、踩油门。</p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 204:
			str = "<p>前期准备</p>\n<p><br>1首先：调整座椅位置，使踩踏离合器和脚刹、油门时较舒服（尤其是便于使离合器处于半离合状态）。</br><br>2其次：调整左后视镜，使车身占后视镜约1/5（即在左后视镜中可以看见车左后下方）。</br><br>3然后：调整右后视镜，使车身占后视镜约1/5（即使右后门把手在右后视镜的左下方）。</br></p><p>评判标准</p>\n<p><br>1车辆入库停止后，车身出线的，不合格。</br><br>2中途停车的，不合格。</br><br>3行驶中轮胎压车道边线的，扣10分。</br></p><p>操作步骤</p>\n<p><br>1车身在车库的左侧，右轮沿着库左边线行驶，车轮距左边线50cm（从座位上看，桑塔纳一般在车后侧机盖1/3的位置压线），行驶到库上边线以前50cm处停车。</br><br>2准备倒车时，开左转向灯，然后开始倒车。从右面车窗中柱看2号角，2号角离中柱5cm时，向右打1.5圈（打死）。</br><br>3当左侧后视镜出现4号点（即车库的右后角）时向左回轮1.5圈（即回正方向），继续倒车。</br><br>4从左后视镜看左后车轮，马上快碰到边缘线时，向左打死方向到车证（两侧后视镜观察到车身与车库边线平行）停车。</br><br>5出库：开左转向灯，方向盘不用回，挂1档，档看到机盖最右侧越过左前库1号点时，向右回轮1.5圈，继续前进进到右侧雨刷最后一节库左线时，向右打一圈，车正回1圈，向前行驶。关转向灯，考试结束</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 205:
			str = "<p>操作要求</p>\n<p><br>车辆从弯道的一端前进驶入，减速换挡，以低挡低速从另一端驶出，行驶中不得挤压路边缘线，方向运用自如。</br></p><p>评判标准</p>\n<p><br>1中途停车，不及格;</br><br>2扎压路牙，扣20分;</br><br>3熄火，扣20分;</br></p><p>操作方法</p>\n<p><br>1进入弯道前，降低车速，用1挡或2挡驶入S形路，车辆与路右侧保持0.5m左右的距离，适当修正方向(留出左侧内轮差的足够距离)。</br><br>2进入第一个弯道，车辆沿道路的右侧进入弯道(距左侧路边缘约1米)，保持匀低速行驶，向左打方向盘，车辆由靠右侧行驶变为靠左侧行驶。</br><br>3进入第二个弯道，车辆左侧车轮保持与路边缘线0.5m，适度修正方向。</br><br>4出弯道时，回转方向盘，进入直线行驶。</br></p><p>考试技巧</p>\n<p></br>考场其实就是一条“S”形道路，只是路宽仅3.5米。一要保持全程一档行驶，打方向不可过急;二是驶向右弯道时，右轮贴着其右边路牙，反之亦然。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 206:
			str = "<p>操作要求</p>\n<p><br>用低速按规定的行驶路线﹐不停车地一次90度急转弯通过直角。如转不过弯﹐可借助一次倒车﹐但要被扣10分。</br></p><p>评分标准</p><p><br>1车轮触压突出点﹐不及格</br><br>2车轮触压路牙一次﹐扣20分</br><br>3借助倒车﹐扣10分</br></p><p>操作方法</p><p><br>进入直角弯前，先打右转向灯，车头快到直角弯入口路口路宽1/2处时，向右打轮1圈半，尽量让车身靠右贴，远离路牙。而靠右贴的合适位置，以路边出现在车头右起1/3处为宜，此时把轮回正，车往前直行，前方视野中左侧反光镜下沿即将看到路牙时，向左连续打轮2圈(速度不要过猛)。当路边出现在车头1/2处时，向右回轮1圈，打右转向灯。当车与路快正时，再向右回轮1圈，使出直角弯。</br></p><p>驾驶技巧</p><p><br>1一挡通过﹐不加油门;</br><br>2驶入考场时尽量紧贴右侧路牙;</br><br>3正前方的路牙一旦被引擎盖挡住视线，就要向左猛打方向，直至打到底;</br><br>4看到车头对准出口，就迅速将方向回正。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 207:
			str = "<p>操作要求</p>\n<p><br>整个考场为一个“凸”字型，考生驾车进入考场后开始考试，此时，“凸”字上方凸起的车库应在车辆前进方向右侧，考生应先将车辆驾驶至“凸”字左侧尽头边线前，然后向右后方倒车入库，中间不能停顿。入库后向左前方行驶至“凸”字右侧尽头边线前，再向左后侧倒车入库，完成考试。</br></p><p>评判标准</p>\n<p><br>1不按规定路线、顺序行驶的，不合格;</br><br>2车身出线的，不合格;</br><br>3倒车不入的，不合格;</br><br>4中途停车的，不合格。</br><br>5超过4分钟未完成倒车入库不合格。</br></p><p>操作方法</p>\n<p>右倒库</p>\n<p><br>1轻抬离合使车后移，控制好车速，看左后视镜，当右起点感应线出现在左后视镜位置时把转向盘右打死。</br><br>2把转向盘右打死，继续后移，中间不能停车，同时观察右后视镜，至最右边线露出后，保持车身与库角距离30CM(小于30CM回半圈方向盘调整，大于30CM继续右打死方向盘，车身正还是大于30CM，可能是看1点时方向盘打的慢，下次提前或加快打方向速度)。继续后移，当右后视镜库边线与车身平行时，方向盘回正，调正车身，倒车入库。</br><br>3倒库入底时看左后视镜，当库前边线在左后视镜位置时，停车，倒库完成。</br><br>4出库，挂一档前行，当车前头盖刚看不到路边线时，把转向盘左打死，当车身正时，回正方向，前车轮过左起点感应线，停车。</br></p></br><p>左倒库</p>\n<p><br>5轻抬离合使车后移，看左后视镜，当左起点感应线稍微过左后视镜，到位置时把转向盘左打死。</br><br>6把转向盘左打死，继续后移，中间不能停车，同时观察左后视镜，至最左边线露出后，保持车身与库角距离30CM(小于30CM回半圈方向盘调整，大于30CM继续右打死方向盘，车身正还是大于30CM，可能是看1点时方向盘打的慢，下次提前或加快打方向速度)。继续后移，当左后视镜库边线与车身平行时，方向盘回正，调正车身，倒车入库。</br><br>7方向盘回正，倒库入底。看左后视镜，当库前边线出在左后视镜位置时，停车，倒库完成。</br><br>8出库，挂一档前行，当车前头盖刚看不到路边线时，把转向盘右打死，当车身正时，回正方向，前车轮过左起点感应线，停车。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
			// 范围在[3201,315]内的是科目三点击条目后跳转的详情
			case 301:
			str = "<p>驾驶技巧</p>\n<p>上车认证。学员到达指定考试车辆，车载系统对考生进行信息对比（指纹或路考卡）、监控拍照。在考生身份被确认后，系统自动发出“上车准备”指令，考生（下车）向考官报道，科目三考试正式开始。</br><br>②车外检查。考生听到“上车准备”口令后，首先逆时针绕车一周，检查车辆外部和周围安全，然后走到考官门前报告情况，请求上车。系统判断考生是否完成绕车一周的关键点是开门关门的动作。如考生绕车一周前开门，系统会判没有绕车一周。绕车一周的顺序是：左后轮-车尾部有没有障碍物-右后轮-右前轮-车前部有无障碍物-左前轮。</br><br>③车内准备。当考生车外检查一周后，即可来到左车门前，观察前后交通情况，确保无碍后，左手拉开车，右脚迈进，右手抓住方向盘，顺势落座。左脚跟进，随手关紧车门，向考官递上身份证件，礼貌地说：“报告老师，我是XXX，请多关照。”然后，调整主驾椅，检查后视镜，系上安全带，检查手制动，挂入空挡位，请示考试员，启动发动机（夜间开大灯，关闭警示灯），观察仪表情况，报告安全员：“仪表正常，是否起步？”</br></p><p>评判标准</p>\n<p><br>①车门上车前，不绕车一周检查车辆外观及安全状况，不及格；</br><br>②打开车门前，不观察后方交通情况，不合格；</br><br>③启动机器前，不调整驾驶座椅、后视镜、检查仪表，扣5分；</br><br>④启动机器时，变速器操纵杆未置于空挡（驻车挡），扣10分；</br><br>⑤启动机器后，不及时松开启动开关，扣10分；</br><br>⑥启动机器后，车门未关闭，不合格；</br><br>⑦启动机器后，不系安全带，不合格。</br></p><p>通关秘籍</p>\n<p><br>慢抬离合把油加，左右观察松手刹。</br><br>拨转向、鸣喇叭；左脚踏、右手挂。</br><br>轻点油门要跟上，缓松离合半联动。</br><br>确保安全手刹放，观看内外后视镜。</br><br>左转灯开喇叭响，脚踩离合挂一挡。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 302:
			str = "<p>驾驶技巧</p>\n<p><br>①点火发动：上车准备结束，系统发出“请准备完毕后立即起步”指令。考生启动发动机，系统发布“请起步”指令。听到起步指令，考生应在一分钟内完成起步动作。</br><br>②车辆起步：左脚踩踏离合，右手挂一挡，打开左转向灯（夜间起步变换灯光三下，夜间超车或会车变换灯光两下），鸣喇叭两声，看左右后视镜，松开手刹，慢抬离合，轻点油门，车辆起步。</br><br>③汇入车流：车辆起步后，看左后视镜，在保证安全的情况下，轻踩油门，挂入二挡，向左轻打方向盘，驶入行车道，向右回正方向，关闭转向灯。</br></p><p>评判标准</p>\n<p><br>①起步前，不侧头看后视镜，观察左、后方交通情况，不合格；</br><br>②起步前，不开左转向灯，扣20分；开左转向灯少于3秒，扣10分；</br><br>③起步超过1分钟，判“不按照考试员指令驾驶，考试不合格”，不松驻车制动器，扣10?分；</br><br>④起步时不松驻车制动器，扣10分；</br><br>⑤起步时车辆发生闯动，扣10分；</br><br>⑥起步时发动机熄火一次，扣10分；</br><br>⑦起步时加速踏板控制不当，致使发动机转速过高，扣15分；</br><br>⑧起步时车辆后溜距离大于30厘米，不合格；小于30厘米，扣20分；</br><br>⑨起步时不能合理使用喇叭（鸣笛两声），扣10分；</br><br>⑩夜间行车时不能正确开启灯光（远光灯、近光灯）等，不合格。</br></p><p>通关秘籍</p>\n<p><br>慢抬离合把油加，左右观察松手刹。</br><br>拨转向、鸣喇叭；左脚踏、右手挂。</br><br>轻点油门要跟上，缓松离合半联动。</br><br>确保安全手刹放，观看内外后视镜。</br><br>左转灯开喇叭响，脚踩离合挂一挡。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 303:
			str = "<p>驾驶技巧</p>\n<p><br>①观察交通情况：形式过程要适时（每隔20秒）观察内、外后视镜，视线不得离开行驶方向超过2秒，时刻观察前后车况、跟车距离、车道路况，时刻准备减速慢行，礼让三先。</br><br>②控制挡位车速：不刻意要求挡位车速。但要求全程必须有达到四挡的操作，因此，要根据交通状况适时用换挡制动控制车速。如遇前车制动、车距缩短、路面障碍要及时采取减速措施（减挡或点刹）。如遇前车加速、车距较大、路况较好，可适时采取加速措施（加挡或跟油）。</br><br>③保持直线行驶：不论车速高低，都要控制直行，方向稳定控制以车辆行驶方向偏差角度不超过10度为标准。在行驶过程中，要跟盯前方80-100米处，根据行车方向偏角随时调整方向（左手为主，少转少回）。</br></p><p>评判标准</p>\n<p><br>①方向控制不稳，不能保持车辆直线运动状态，不合格；</br><br>②遇前车制动时不采取减速措施，不合格；</br><br>③超过20秒不通过后视镜观察后方交通情况，扣10分；</br><br>④不了解车辆行驶速度，扣10分；</br><br>⑤未及时发现路面障碍物，未及时采取减速措施，扣10分。</br></p><p>操作要求</p>\n<p><br>车辆在百米内依次完成一挡加到五挡，再从五挡减至二挡的过程，不能越级加减挡。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 304:
			str = "<p>驾驶技巧</p>\n<p><br>①加减挡位考察考生对挡位操作控制的技能，其基本要求是速度匹配，及时平稳，不准越级。挡位与车速匹配关系为：一挡车速不超过20km/h；二挡车速不超过30km/h；三挡车速不超过40km/h；四挡车速不低于30km/h。</br><br>②在考试过程中，不单独发加减挡指令，系统根据全程实际换挡情况作出判断，要求1挡连续行驶不超过100米。2挡连续行驶不超过200米，全程1挡与2挡行驶路程总和不超过500米。</br><br>③加减挡位要求与车速匹配，加挡要加油冲车，减挡要空油轰车。换挡时先踩离合后摸挡杆，踩离合时松油门，踩油门时松离合。</br><br>④每次换挡前，要看一下左后视镜，确保安全时再换挡。加挡：看左镜-加油门-松离合-松油门-放空挡-踩离合-加一挡。减挡：看左镜-松离合-松油门-放空挡-抬离合-轰空油-踩离合-减一挡。</br></p><p>评判标准</p>\n<p><br>①在换挡过程，如果用眼睛看着变速器操控杆换挡视为不合格；</br><br>②车辆运行速度要与挡位匹配；</br><br>③根据路况和车速，合理加减挡，换挡要及时、平顺；</br><br>④要按指令平稳加减挡。</br></p><p>通关秘籍</p>\n<p><br>①加挡操作：加油行车稳方向，一踏离合油门放，速回空挡换新挡，缓抬离合油跟上。</br><br>②减挡操作：减挡速度要适当，一踏离合油门放，同时放空挡，迅速抬起离合器，空油轰车要适当，再踏离合换新挡，离合缓抬油跟上。</br></p><p>操作要求</p>\n<p><br>变更车道简称并，机动车驾驶人需根据道路状况和交通流量情况，选择变道的地点、时机，掌握正确的变道方法。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 305:
			str = "<p>驾驶技巧</p>\n<p><br>①系统听到“变更车道”指令后，考生必须在100米内完成，且时间不能超过1分钟。</br><br>②在考试过程中，究竟是向左还是向右变更车道，考生根据实际交通情况自主决定；向当前行驶车道的临近车道变更。</br><br>③变更车道时，要保持与目标车道上行驶车辆的安全距离，控制行驶速度，不得妨碍其他车辆的正常行驶。车向哪拐就向哪拨转向灯，看哪边的后视镜，向哪边打方向盘改道。</br></p><p>评判标准</p>\n<p><br>①变更车道前，不通过内、外后视镜观察后方道路交通情况，不合格；</br><br>②变更车道时，判断车辆安全距离不合理，妨碍其他车辆正常行驶，不合格；</br><br>③连续变更两条以上车道，不合格。</br></p><p>通关秘籍</p>\n<p><br>沉着冷静要放松，变道完毕要关灯。</br><br>左右转向要开灯，安全车距要适中。</br><br>左顾右看要分清，之前要看后视镜。</br><br>根据路况做决定，变道只有一分钟。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 306:
			str = "<p>操作要求</p>\n<p><br>在完成规定的考试项目后，考试员发出“靠边停车”指令，考生听到命令后准备停车。</br></p><p>驾驶技巧</p>\n<p><br>①打右转向灯。</br><br>②观察右后视镜(要有明显的摆头动作，要不会被误认为不看后视镜的)，观察周围环境。</br><br>③松油减速。</br><br>④动方向，确定停车位。</br><br>⑤停车后车辆距离道路右侧边缘线或人行道边缘距离30CM以内。</br><br>⑥停车后先拉手刹再回空挡，将转向灯回正并将发动机熄火，在车内开门前要先看左后视镜(做侧头动作)，观察侧后方和左侧交通情况再推门下车，下车后关好车门。</br><br>⑦夜间在路边停车时要关闭前照灯、开启警示灯，做好安全防护措施，以免发生事故。</br></p><p>评判标准</p>\n<p><br>①停车前，不通过内、外后视镜观察后方和右侧交通情况的，不合格；</br><br>②考试员发出靠边停车指令后，未能在规定的距离内停车的，不合格；</br><br>③停车后，车身超过道路右侧边缘线或者人行道边缘的，不合格；</br><br>④停车后，在车内开门前不侧头观察侧后方和左侧交通情况的，不合格；</br><br>⑤下车后不关闭车门的，不合格；</br><br>⑥停车后，车身距离道路右侧边缘线或者人行道边缘大于30cm的，扣10分；</br><br>⑦停车后，未拉紧驻车制动器的，扣10分；</br><br>⑧拉紧驻车制动器前放松行车制动踏板的，扣10分；</br><br>⑨下车前不将发动机熄火的，扣5分。</br></p><p>通关秘籍</p>\n<p><br>靠边停车先观察，弯道出口都不中;</br><br>油库车站和码头，狭道桥梁应畅通。</br><br>路有人车皆不宜，选好位置打灯光;</br><br>特殊地形须细辨，安全通畅好地方。</br><br>若然考官有指令，报告考官先别慌;</br><br>减速靠右三五十，停正停稳忌急冲。</br><br>停稳车后拉手刹，再置排挡空挡中;</br><br>左脚抬放离合下，右脚油板悬上空。</br><br>左手关闭转向灯，双手扶于盘侧方;</br><br>报告考官考试完，取带听令离车中。</br><br>左手打开车前门，确认车左无车通。</br><br>人车安全左先出，右侧跟出面右方。</br></p>";			tvContent.setText(Html.fromHtml(str));
			break;
		case 307:
			str = "<p>驾驶技巧</p>\n<p><br>①听到系统发出通过路口的语言指令后，考生不一定立即做动作，但必须到达停车横线位置前50米内完成变灯、观察、减速（或刹车）等操作。</br><br>②路口直行：打转向灯，看后视镜，在虚线区驶入直行车道，踩离合微带刹车减速至17-8速，3挡变2挡（速度太慢变1挡），观察左右交通情况，稳住方向慢行礼让直行通过路口。</br><br>③路口左转：开左转灯，看左视镜，在虚线区驶入左转车道，踩离合微带刹车减速至17-8速，3挡变2挡（速度太慢变1挡），观察左侧交通情况，左转方向慢行礼让左转通过路口。</br><br>④开右转灯，看右视镜，在虚线区驶入右转车道，踩离合微带刹车减速至17-8速，3挡变2挡（速度太慢变1挡），观察右侧交通情况，右转方向慢行礼让右转通过路口。</br></p><p>评判标准</p>\n<p><br>①通过路口要根据指令选准车道（直行、左转和右转），选错不合格；</br><br>②通过路口不按规定避让行人和优先通行的车辆，不合格；</br><br>③通过路口不按规定减速慢行或停车瞭望，不合格；</br><br>④直行通过路口，不观察左、右方交通情况，不合格；</br><br>⑤转弯通过路口，未观察侧前方或侧后方交通情况，不合格；</br><br>⑥转弯通过路口，不打转向灯扣20分，少于3秒扣10分；</br><br>⑦左转通过路口，未靠路口中心点左侧转弯，不合格；</br><br>⑧路口交通阻时，将车辆驶入路口内等候，不合格</br>；<br>⑨参加夜间考试，通过路口前要使用远近灯交替示意。</br></p><p>通关秘籍</p>\n<p><br>一气呵成向前行，注意礼让别心急。</br><br>挡位变换要分清，稳住离合踩刹车。</br><br>左顾右盼听号令，打转向看后视镜。</br><br>50米内要完成，虚线驶入要冷静。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 308:
			str = "<p>驾驶技巧</p>\n<p><br>①通过人行横道：提前减速，鸣笛示意，观察两侧交通情况，确认安全后，合理控制车速通过，遇行人停车让行。</br><br>②通过学校区域：提前减速、禁止鸣笛，观察情况，文明礼让，确保安全通过，遇有学生横过马路及时停车让行。</br><br>③通过公交车站：提前减速，观察公共汽车进、出站动态和乘客上下动态，着重注意同向公共汽车前方或对向公共汽车后方有无行人横穿马路。</br></p><p>评判标准</p>\n<p><br>①不观察左、右交通情况，不合格；</br><br>②不按规定减速慢行，不合格；</br><br>③遇行人通过人行横道不停车让行，不合格；</br><br>④不主动避让优先通过车辆、行人、非机动车等，不合格；</br><br>⑤夜间考试，必须使用远近灯交替示意。</br></p><p>通关秘籍</p>\n<p><br>在实际道路驾驶中，如遇车辆、人流密集，一定要减速换低挡，必要时宁可停车，千万莫犹豫不决，让考官踩了制动，肯定不及格。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 309:
			str = "<p>驾驶技巧</p>\n<p><br>系统发出会车指令后，考生要正确判断会车地点，会车有危险时，控制车速，提前避让，调整会车地点，会车时与对方车辆保持安全间距。会车的要领是：刹车减速，靠右行驶。</br></p><p>评判标准</p>\n<p><br>①在没有中心隔离设施或中心线的道路上会车时，不减速靠右行驶，与行人、他车未保持安全距离，不合格；</br><br>②会车困难时不让行，不合格；</br><br>③横向安全间距判断差，紧急转向避让相对方向过来车，不合格；</br><br>④夜间考试，按照规定使用远近灯交替示意。</br></p><p>操作要求</p>\n<p><br>系统发出超车指令后，要在一分钟之内完成超车动作。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 310:
			str = "<p>驾驶技巧</p>\n<br>①超车前，保持与被超车辆的安全跟车距离，从后视镜观察两侧交通情况，开启左转向灯，看后视镜，选择合理时机，鸣喇叭（夜间加交替远近光灯），从被超车辆的左侧超越。</br><br>②超车时，侧头观察被超越车辆的动态，保持横向安全距离。</br><br>③超越后，要保持直线行驶至少10秒，在不影响被超越车辆的情况下，开启右转向灯，看右后视镜，逐渐驶回原车道。</br></p><p>评判标准</p>\n<br>①超车前不通过内、外后视镜观察后方和左侧交通情况，不合格；</br><br>②超车时机选择不合理，影响其他车辆正常行驶，不合格；</br><br>③超车时未与被超越车辆保持安全距离，不合格；</br><br>④超车后急转向驶回本车道，妨碍被超车辆正常行驶，不合格；</br><br>⑤从右侧超车，不合格；</br><br>⑥当后车发出超车信号时，具备让车条件不减速靠右让行，扣10分。</br></p><p>操作要求</p>\n<p><br>汽车掉头是指汽车行驶方向作180度改变。此项目是考核机动车驾驶人利用道路路幅，确定转向掉头和转向速度的能力。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 311:
			str = "<p>驾驶技巧</p>\n<p><br>①顺道掉头。考官发出顺道掉头指令，如果道路单向只有一条车道，掉头时开左转向灯，借右边人行道掉头，线路如灯泡状。</br><br>②路口掉头。考官发出路口掉头指令，如果路口有红绿灯，务必等绿灯亮时，借右边人行道画灯泡状掉头；如果路口没有红绿灯，务必先把车辆停下来观察四周、确定安全后画灯泡状掉头。一般地，多数单向一条车道的路口会分直行左转弯和右转弯两条车道，这时务必选直行左转弯车道掉头。</br><br>③两车道掉头。听到口令或见掉头标志，踩离合带刹车降低车速，换1挡抬离合器，开左转向灯，先往右转一圈方向，迅速往左2把，掉头后适时回正方向。</br><br>④四车道掉头。听到口令，开左转灯，变道进入快车道，再开左转灯，踩离合带刹车放慢车速，换1挡，先往右转一圈方向，迅速往左2把，掉头后适时回正方向，掉头进入快车道，过了白线变入慢车道。/<br></p><p>评判标准</p>\n<br>①掉头前，不能正确观察交通情况选择掉头时机，不合格；</br><br>②掉头前，掉头地点选择不当，不合格；</br><br>③掉头前，要先发出掉头信号；</br><br>④掉头时，妨碍正常行驶的其他车辆和行人通行，不合格；</br><br>⑤掉头时，一定要减速，缓慢掉头；</br><br>⑥掉头时，应将双脚放在离合、脚刹上方，做好随时停车准备。</br><p>操作要求</p>\n<p><br>①和机动车、非机动车、行人会车时，需距对面来车150米以外，互相关闭远光灯，改为近光灯。如果对面来车没有关闭远光灯，可变换远近光灯示意对面来车关闭远光灯。</br><br>②超车时，变换远近光灯示意前车让路。</br><br>③在行驶过程中，发现后车用远近光灯示意超车，只要有让路条件，就必须及时减速、靠右让路。</br><br>④跟车行驶时，不要使用远光灯，要使用近光灯。</br><br>⑤在有路灯照明的道路行驶时，不要使用远光灯。</br><br>⑥在车辆掉头或遇到交叉路口时，不要使用远光灯。</br><br>⑦起步前，关闭双闪灯，停车后，开启双闪灯。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 312:
			str = "<p>驾驶技巧</p>\n<p><br>①利用耳朵判断</br><br>黑夜路况难以判断，视觉在黑暗中受阻，可利用耳朵判断。一般来说，如果感到发动机声音变沉闷、同时车速变缓，说明行驶阻力增加，汽车可能正行驶在上坡或松软路面上。如果感觉发动机编的轻快、车速自动加快时，说明行驶中阻力减小，汽车可能正行驶于一段下坡路中。</br><br>②借助光线判断</br><br>第一，视觉适应。人的视线系统是有着很强适应性的，黄昏时分，可以在确保安全的前提下，只开示宽灯，让眼睛适应一下光线不足的的情况，而到了夜晚，有了近光灯的辅助，视线也会更好一些。</br><br>第二，无月行车。一些老驾驶员总结出了夜间行车“走灰不走黑”的原则。也就是在没有月光的夜晚，路面一般为灰色，路面以外一片黑色。有水坑的地方会显得更亮，而坑洼处会更暗黑。</br><br>第三，光柱变化，一些老司机还积累了根据前灯光柱变化情况来判断地形的经验。如光柱变短可能是遇上弯道或上坡路，光柱边长也可能是下坡路，光柱有缺口可能是路上坑洼等。</br><br>③巧用灯光判断</br><br>有的时候，注意其他车辆的灯光照射情况也可以起到很巧妙的作用。例如，夜间行车常遇到交叉路口，可根据对向来车的灯光照射，预测对方行驶情况，如路口打焦射的散射光，可判断车距交叉路口尚远，如大灯光有光束或在路口拐角处树梢上有明亮的光线或电线杆、影壁（多见于T字型路口），做好让行的措施，在天气好的情况下，如对方是远光灯直射光线，且距离远又清楚，可判断前方道路平坦；如远光灯光线突然消失或者不再出现，可判断前方有路口或弯道；如远光灯光线左右大幅度摆动、可判断前方是弯曲道路，如远光灯光线上下浮动可判断前方是破路。</br><p>评判标准</p>\n<p><br>①不正确开启灯光，不合格；</br><br>②同方向近距离跟车行驶时，使用远光，不合格；</br><br>③通过急弯、坡路、拱桥、人行横道或没有交通信号灯控制的路口时，不交替使用远、近光灯示意，不合格；</br><br>④会车时不按规定使用灯光，不合格；</br><br>⑤通过路口时使用远光等，不合格；</br><br>⑥超车时未变换使用远近光灯，不合格；</br><br>⑦在有路灯、照明良好的道路上行驶时，使用远光灯，不合格；</br><br>⑧进入无照明、照明不良的道路行驶时，要使用远光灯；</br><br>⑨对低能见度道路情况判断差不合格；</br><br>⑩在路边临时停车要关闭前照灯并开启示廓灯。</br></p><p>通关秘籍</p>\n<p><br>夜间行驶要慢行，起步前开灯来照明。</br><br>使用灯光要正确，各种情况要分清。</br><br>无照明路用远光，照明良好的用近光。</br><br>超车过路交替用，提前示意早提示。</br><br>上车喊报告！</br><br>拉门出左脚，坐好叫考官，面带梨涡笑。</br><br>关门深呼吸，坐姿要摆好。</br><br>查看空挡位，点火要轻柔。</br><br>方向别忘了，挂挡要干脆，手刹要放掉。</br><br>起步问考官，多看后视镜，喇叭来欢叫。</br><br>离合器放松，做好半连动，出发要稳当，别让考官晃！</br><br>换挡要及时，加挡要迅速，遇人要刹车，可别过了头。</br><br>转弯要减速，立即减下挡，多看多停顿，安全放心中。</br><br>肩膀要放松，双手放到位，神态要自然，莫因考官慌。</br><br>自己有信心，艰辛都不怕，胆大又心细，礼貌别忘了。</br><br>只要牢记着，考试准能过！</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 313:
			str = "<p><br>上车喊报告！</br><br>拉门出左脚，坐好叫考官，面带梨涡笑。</br><br>关门深呼吸，坐姿要摆好。</br><br>查看空挡位，点火要轻柔。</br><br>方向别忘了，挂挡要干脆，手刹要放掉。</br><br>起步问考官，多看后视镜，喇叭来欢叫。</br><br>离合器放松，做好半连动，出发要稳当，别让考官晃！</br><br>换挡要及时，加挡要迅速，遇人要刹车，可别过了头。</br><br>转弯要减速，立即减下挡，多看多停顿，安全放心中。</br><br>肩膀要放松，双手放到位，神态要自然，莫因考官慌。</br><br>自己有信心，艰辛都不怕，胆大又心细，礼貌别忘了。</br><br>只要牢记着，考试准能过！</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 314:
			str = "<p><br>①上车前，无论你在车辆的什么位置，请务必从车的右侧绕过车头走到驾驶室门前，先观察车前道路上是否有障碍，再观察车后方是否有来车，确保安全后，打开车门，上车。</br><br>②轻轻关闭车门，将事先准备好的身份证件，用双手递给考官，面带微笑做自我介绍：“考官您好，我是XX驾校学员，我叫XXX，这是我的证件。”</br><br>③尽可能地比较熟练的快速调整好座椅、左右中3个观后镜，系好安全带，观察仪表，如果仪表正常，微笑着向考官征询意见：“报告考官，仪表正常，请问是否可以起步？”</br><br>④得到允许后，踩离合，挂1挡，打左转向灯，按喇叭、观察后视镜，确认安全后，松离合到半连动，右手同时放掉手刹（可有效防止上坡起步后溜），平稳起步。</br><br>⑤顺利起步后，不要急于立即向左变更车道，继续直行，同时关闭左转向灯，踩离合，换到2挡（陡坡起步可以一直用1挡爬到坡顶再换挡），慢一点松离合，右脚同时给一点油门防止冲车或者熄火。加油保持2挡前进，观察速度表到达30公里以上或者发动机转速超过2000，换到3挡行驶，保持30的速度行驶，如果考官不要求加速，请不要在他面前炫耀你的技术而开到60公里以上，那样只会让他感觉你喜欢开快车，不安全。行进过程中，千万别忘记隔个10多秒就看一下后视镜哦。</br><br>⑥当听到“靠边停车”的口令后，你距离成功很近了，不要慌，第一反应是打右转向灯，然后脑袋做个明显的偏转动作去看右边的后视镜，如果右边后方没有车辆，请向右打方向，同时踩刹车，左脚离合也跟着踩下去，用平时你定点停车的感觉使车距离右边路沿30CM的时候，回正方向，将车停稳在路边。</br><br>⑦车辆停稳后，拉手刹，拨回空挡，双脚松开离合刹车，关掉右转向灯，松开安全带，面向考官说：“谢谢考官！”然后观察左镜子，确认后方没来车后，开车门下车。下车后，用双手轻轻关上车门，再次说声：“谢谢考官！”</br>综述：路考是人考，人为的因素很重要，谦逊的态度和礼貌能弥补你的技术不足，甚至能挽救你的结局，对考官礼貌点，甚至巴结点，相信我有好处的；路考是考动作，不是考速度，所以一定按程序想好，做完该做的动作而不是追求挂上5挡冲到100码。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 315:
			str = "<p><br>①提速并线。</br><br>正常路况下，常见新手司机一脚刹车，然后慢慢并线。此行为极易招致事故，至少会立即引起堵车。应当在打转向灯后，看准安全的空挡，稍微提速并同时打轮并线，尽量使自己的车速比后车快10KM左右，以免后车处理不及。</br><br>②刹车时观察后视镜。</br><br>现在路上车距都较近，刹车时顺带注意一下后车距离，如果距离太近，并且自己与前车还有一定的距离，就稍微松一点刹车，避免追尾。</br><br>③过弯技巧。</br><br>弯前减速，弯半加速，又快又稳。入弯过急然后再踩刹车，很容易甩尾或侧倾。</br><br>④减挡超车。</br><br>需要快速超车时，先减一个挡位，使发动机输出更大的牵引力（仅适用于手动挡车）。另外，山路超车时，先尽量贴近前车，减少超车距离。</br><br>⑤在双向混行车道行驶时尽量靠左。</br><br>很多新手害怕与对面来车剐蹭，喜欢稍微靠右一点。需要注意的是，右面是行人，发生事故是“铁撞肉”，麻烦大大。左面发生事故最多是“铁蹭铁”，孰重孰轻一想便知。当然，提高技术安全驾驶更重要。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 501:
			str = "<p>一、考试内容</p><p><br>1道路交通安全法律、法规和规章;</br><br>2交通信号及其含义;</br><br>3安全行车、文明驾驶知识;</br><br>4高速公路、山区道路、桥梁、隧道、夜间、恶劣气象和复杂道路条件下的安全驾驶知识;</br><br>5出现爆胎、转向失控、制动失灵等紧急情况时的临危处置知识;</br><br>6机动车总体构造、主要安全装置常识、日常检查和维护基本知识;</br><br>7发生交通事故后的自救、急救等基本知识，以及常见危险物品知识。</br></p><p>二、合格标准<p><p><br>满分为100分，成绩达到90分的为合格。</br><br>考试由申请人通过计算机闭卷答题，考试时间为45分钟。科目一共考试100道题，由计算机考试系统从考试题库中随机抽取生成。</br><br>科目一考试重在掌握科学的学习方法，反复记忆练习。</br><p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 502:
			str = "<p>一、机动车驾驶人有下列违法行为之一，一次记12分：</p><p><br>1驾驶与准驾车型不符的机动车的;</br><br>2饮酒后驾驶机动车的;</br><br>3驾驶营运客车(不包括公共汽车)、校车载人超过核定人数20%以上的;</br><br>4造成交通事故后逃逸，尚不构成犯罪的;</br><br>5上道路行驶的机动车未悬挂机动车号牌的，或者故意遮挡、污损、不按规定安装机动车号牌的;</br><br>6使用伪造、变造的机动车号牌、行驶证、驾驶证、校车标牌或者使用其他机动车号牌、行驶证的;</br><br>7驾驶机动车在高速公路上倒车、逆行、穿越中央分隔带掉头的;</br><br>8驾驶营运客车在高速公路车道内停车的;</br><br>9驾驶中型以上载客载货汽车、校车、危险物品运输车辆在高速公路、城市快速路上行驶超过规定时速20%以上或者在高速公路、城市快速路以外的道路上行驶超过规定时速50%以上，以及驾驶其他机动车行驶超过规定时速50%以上的;</br><br>10连续驾驶中型以上载客汽车、危险物品运输车辆超过4小时未停车休息或者停车休息时间少于20分钟的;</br><br>11未取得校车驾驶资格驾驶校车的。</br></p><p>二、机动车驾驶人有下列违法行为之一，一次记6分：</p><p><br>1机动车驾驶证被暂扣期间驾驶机动车的;</br><br>2驾驶机动车违反道路交通信号灯通行的;</br><br>3驾驶营运客车(不包括公共汽车)、校车载人超过核定人数未达20%的，或者驾驶其他载客汽车载人超过核定人数20%以上的;</br><br>4驾驶中型以上载客载货汽车、校车、危险物品运输车辆在高速公路、城市快速路上行驶超过规定时速未达20%的;</br><br>5驾驶中型以上载客载货汽车、校车、危险物品运输车辆在高速公路、城市快速路以外的道路上行驶或者驾驶其他机动车行驶超过规定时速20%以上未达到50%的;</br><br>6驾驶货车载物超过核定载质量30%以上或者违反规定载客的;</br><br>7驾驶营运客车以外的机动车在高速公路车道内停车的;</br><br>8驾驶机动车在高速公路或者城市快速路上违法占用应急车道行驶的;</br><br>9低能见度气象条件下，驾驶机动车在高速公路上不按规定行驶的;</br><br>10驾驶机动车运载超限的不可解体的物品，未按指定的时间、路线、速度行驶或者未悬挂明显标志的;</br><br>11驾驶机动车载运爆炸物品、易燃易爆化学物品以及剧毒、放射性等危险物品，未按指定的时间、路线、速度行驶或者未悬挂警示标志并采取必要的安全措施的;</br><br>12以隐瞒、欺骗手段补领机动车驾驶证的;</br><br>13连续驾驶中型以上载客汽车、危险物品运输车辆以外的机动车超过4小时未停车休息或者停车休息时间少于20分钟的;</br><br>14驾驶机动车不按照规定避让校车的。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 503:
			str = "<p>驾驶技巧</p>\n<p>上车认证。学员到达指定考试车辆，车载系统对考生进行信息对比（指纹或路考卡）、监控拍照。在考生身份被确认后，系统自动发出“上车准备”指令，考生（下车）向考官报道，科目三考试正式开始。</br><br>②车外检查。考生听到“上车准备”口令后，首先逆时针绕车一周，检查车辆外部和周围安全，然后走到考官门前报告情况，请求上车。系统判断考生是否完成绕车一周的关键点是开门关门的动作。如考生绕车一周前开门，系统会判没有绕车一周。绕车一周的顺序是：左后轮-车尾部有没有障碍物-右后轮-右前轮-车前部有无障碍物-左前轮。</br><br>③车内准备。当考生车外检查一周后，即可来到左车门前，观察前后交通情况，确保无碍后，左手拉开车，右脚迈进，右手抓住方向盘，顺势落座。左脚跟进，随手关紧车门，向考官递上身份证件，礼貌地说：“报告老师，我是XXX，请多关照。”然后，调整主驾椅，检查后视镜，系上安全带，检查手制动，挂入空挡位，请示考试员，启动发动机（夜间开大灯，关闭警示灯），观察仪表情况，报告安全员：“仪表正常，是否起步？”</br></p><p>评判标准</p>\n<p><br>①车门上车前，不绕车一周检查车辆外观及安全状况，不及格；</br><br>②打开车门前，不观察后方交通情况，不合格；</br><br>③启动机器前，不调整驾驶座椅、后视镜、检查仪表，扣5分；</br><br>④启动机器时，变速器操纵杆未置于空挡（驻车挡），扣10分；</br><br>⑤启动机器后，不及时松开启动开关，扣10分；</br><br>⑥启动机器后，车门未关闭，不合格；</br><br>⑦启动机器后，不系安全带，不合格。</br></p><p>通关秘籍</p>\n<p><br>慢抬离合把油加，左右观察松手刹。</br><br>拨转向、鸣喇叭；左脚踏、右手挂。</br><br>轻点油门要跟上，缓松离合半联动。</br><br>确保安全手刹放，观看内外后视镜。</br><br>左转灯开喇叭响，脚踩离合挂一挡。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 504:
			str = "<p>驾驶技巧</p>\n<br>①超车前，保持与被超车辆的安全跟车距离，从后视镜观察两侧交通情况，开启左转向灯，看后视镜，选择合理时机，鸣喇叭（夜间加交替远近光灯），从被超车辆的左侧超越。</br><br>②超车时，侧头观察被超越车辆的动态，保持横向安全距离。</br><br>③超越后，要保持直线行驶至少10秒，在不影响被超越车辆的情况下，开启右转向灯，看右后视镜，逐渐驶回原车道。</br></p><p>评判标准</p>\n<br>①超车前不通过内、外后视镜观察后方和左侧交通情况，不合格；</br><br>②超车时机选择不合理，影响其他车辆正常行驶，不合格；</br><br>③超车时未与被超越车辆保持安全距离，不合格；</br><br>④超车后急转向驶回本车道，妨碍被超车辆正常行驶，不合格；</br><br>⑤从右侧超车，不合格；</br><br>⑥当后车发出超车信号时，具备让车条件不减速靠右让行，扣10分。</br></p><p>操作要求</p>\n<p><br>汽车掉头是指汽车行驶方向作180度改变。此项目是考核机动车驾驶人利用道路路幅，确定转向掉头和转向速度的能力。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 505:
			str = "<p>驾驶技巧</p>\n<p><br>①加减挡位考察考生对挡位操作控制的技能，其基本要求是速度匹配，及时平稳，不准越级。挡位与车速匹配关系为：一挡车速不超过20km/h；二挡车速不超过30km/h；三挡车速不超过40km/h；四挡车速不低于30km/h。</br><br>②在考试过程中，不单独发加减挡指令，系统根据全程实际换挡情况作出判断，要求1挡连续行驶不超过100米。2挡连续行驶不超过200米，全程1挡与2挡行驶路程总和不超过500米。</br><br>③加减挡位要求与车速匹配，加挡要加油冲车，减挡要空油轰车。换挡时先踩离合后摸挡杆，踩离合时松油门，踩油门时松离合。</br><br>④每次换挡前，要看一下左后视镜，确保安全时再换挡。加挡：看左镜-加油门-松离合-松油门-放空挡-踩离合-加一挡。减挡：看左镜-松离合-松油门-放空挡-抬离合-轰空油-踩离合-减一挡。</br></p><p>评判标准</p>\n<p><br>①在换挡过程，如果用眼睛看着变速器操控杆换挡视为不合格；</br><br>②车辆运行速度要与挡位匹配；</br><br>③根据路况和车速，合理加减挡，换挡要及时、平顺；</br><br>④要按指令平稳加减挡。</br></p><p>通关秘籍</p>\n<p><br>①加挡操作：加油行车稳方向，一踏离合油门放，速回空挡换新挡，缓抬离合油跟上。</br><br>②减挡操作：减挡速度要适当，一踏离合油门放，同时放空挡，迅速抬起离合器，空油轰车要适当，再踏离合换新挡，离合缓抬油跟上。</br></p><p>操作要求</p>\n<p><br>变更车道简称并，机动车驾驶人需根据道路状况和交通流量情况，选择变道的地点、时机，掌握正确的变道方法。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 506:
			str = "<p>驾驶技巧</p>\n<p><br>①顺道掉头。考官发出顺道掉头指令，如果道路单向只有一条车道，掉头时开左转向灯，借右边人行道掉头，线路如灯泡状。</br><br>②路口掉头。考官发出路口掉头指令，如果路口有红绿灯，务必等绿灯亮时，借右边人行道画灯泡状掉头；如果路口没有红绿灯，务必先把车辆停下来观察四周、确定安全后画灯泡状掉头。一般地，多数单向一条车道的路口会分直行左转弯和右转弯两条车道，这时务必选直行左转弯车道掉头。</br><br>③两车道掉头。听到口令或见掉头标志，踩离合带刹车降低车速，换1挡抬离合器，开左转向灯，先往右转一圈方向，迅速往左2把，掉头后适时回正方向。</br><br>④四车道掉头。听到口令，开左转灯，变道进入快车道，再开左转灯，踩离合带刹车放慢车速，换1挡，先往右转一圈方向，迅速往左2把，掉头后适时回正方向，掉头进入快车道，过了白线变入慢车道。/<br></p><p>评判标准</p>\n<br>①掉头前，不能正确观察交通情况选择掉头时机，不合格；</br><br>②掉头前，掉头地点选择不当，不合格；</br><br>③掉头前，要先发出掉头信号；</br><br>④掉头时，妨碍正常行驶的其他车辆和行人通行，不合格；</br><br>⑤掉头时，一定要减速，缓慢掉头；</br><br>⑥掉头时，应将双脚放在离合、脚刹上方，做好随时停车准备。</br><p>操作要求</p>\n<p><br>①和机动车、非机动车、行人会车时，需距对面来车150米以外，互相关闭远光灯，改为近光灯。如果对面来车没有关闭远光灯，可变换远近光灯示意对面来车关闭远光灯。</br><br>②超车时，变换远近光灯示意前车让路。</br><br>③在行驶过程中，发现后车用远近光灯示意超车，只要有让路条件，就必须及时减速、靠右让路。</br><br>④跟车行驶时，不要使用远光灯，要使用近光灯。</br><br>⑤在有路灯照明的道路行驶时，不要使用远光灯。</br><br>⑥在车辆掉头或遇到交叉路口时，不要使用远光灯。</br><br>⑦起步前，关闭双闪灯，停车后，开启双闪灯。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 507:
			str = "<p>操作要求</p>\n<p><br>用低速按规定的行驶路线﹐不停车地一次90度急转弯通过直角。如转不过弯﹐可借助一次倒车﹐但要被扣10分。</br></p><p>评分标准</p><p><br>1车轮触压突出点﹐不及格</br><br>2车轮触压路牙一次﹐扣20分</br><br>3借助倒车﹐扣10分</br></p><p>操作方法</p><p><br>进入直角弯前，先打右转向灯，车头快到直角弯入口路口路宽1/2处时，向右打轮1圈半，尽量让车身靠右贴，远离路牙。而靠右贴的合适位置，以路边出现在车头右起1/3处为宜，此时把轮回正，车往前直行，前方视野中左侧反光镜下沿即将看到路牙时，向左连续打轮2圈(速度不要过猛)。当路边出现在车头1/2处时，向右回轮1圈，打右转向灯。当车与路快正时，再向右回轮1圈，使出直角弯。</br></p><p>驾驶技巧</p><p><br>1一挡通过﹐不加油门;</br><br>2驶入考场时尽量紧贴右侧路牙;</br><br>3正前方的路牙一旦被引擎盖挡住视线，就要向左猛打方向，直至打到底;</br><br>4看到车头对准出口，就迅速将方向回正。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 601:
			str = "<p><br>修订后的《机动车驾驶证申领和使用规定》已经2012年8月21日公安部部长办公会议通过，现予发布，自2013年1月1日起施行，第五章第四节自发布之日起施行。</br><br>第一条根据《中华人民共和国道路交通安全法》及其实施条例、《中华人民共和国行政许可法》，制定本规定。</br><br>第二条本规定由公安机关交通管理部门负责实施。</br><br>省级公安机关交通管理部门负责本省(自治区、直辖市)机动车驾驶证业务工作的指导、检查和监督。直辖市公安机关交通管理部门车辆管理所、设区的市或者相当于同级的公安机关交通管理部门车辆管理所负责办理本行政辖区内机动车驾驶证业务。</br><br>县级公安机关交通管理部门车辆管理所可以办理本行政辖区内低速载货汽车、三轮汽车、摩托车驾驶证业务，以及其他机动车驾驶证换发、补发、审验、提交身体条件证明等业务。条件具备的，可以办理小型汽车、小型自动挡汽车、残疾人专用小型自动挡载客汽车驾驶证业务，以及其他机动车驾驶证的道路交通安全法律、法规和相关知识考试业务。具体业务范围和办理条件由省级公安机关交通管理部门确定。</br><br>第三条车辆管理所办理机动车驾驶证业务，应当遵循严格、公开、公正、便民的原则。</br><br>车辆管理所办理机动车驾驶证业务，应当依法受理申请人的申请，审核申请人提交的材料。对符合条件的，按照规定的标准、程序和期限办理机动车驾驶证。对申请材料不齐全或者不符合法定形式的，应当一次书面告知申请人需要补正的全部内容。对不符合条件的，应当书面告知理由。</br><br>车辆管理所应当将法律、行政法规和本规定的有关办理机动车驾驶证的事项、条件、依据、程序、期限以及收费标准、需要提交的全部材料的目录和申请表示范文本等在办公场所公示。</br><br>省级、设区的市或者相当于同级的公安机关交通管理部门应当在互联网上建立主页，发布信息，便于群众查阅办理机动车驾驶证的有关规定，查询驾驶证使用状态、交通违法及记分等情况，下载、使用有关表格。</br><br>第四条申请办理机动车驾驶证业务的人，应当如实向车辆管理所提交规定的材料，如实申告规定的事项，并对其申请材料实质内容的真实性负责。</br><br>第五条公安机关交通管理部门应当建立对车辆管理所办理机动车驾驶证业务的监督制度，加强对驾驶人考试、驾驶证核发和使用的监督管理。</br><br>第六条车辆管理所应当使用机动车驾驶证计算机管理系统核发、打印机动车驾驶证，不使用计算机管理系统核发、打印的机动车驾驶证无效。</br><br>机动车驾驶证计算机管理系统的数据库标准和软件全国统一，能够完整、准确地记录和存储申请受理、科目考试、机动车驾驶证核发等全过程和经办人员信息，并能够实时将有关信息传送到全国公安交通管理信息系统。</br> </p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 602:
			str = "<p><br>2003年10月28日第十届全国人民代表大会常务委员会第五次会议通过。根据2007年12月29日第十届全国人民代表大会常务委员会第三十一次会议《关于修改〈中华人民共和国道路交通安全法〉的决定》第一次修正。根据2011年4月22日第十一届全国人民代表大会常务委员会第二十次会议《关于修改〈中华人民共和国道路交通安全法〉的决定》第二次修正。</br><br>第一条　为了维护道路交通秩序，预防和减少交通事故，保护人身安全，保护公民、法人和其他组织的财产安全及其他合法权益，提高通行效率，制定本法。</br><br>第二条　中华人民共和国境内的车辆驾驶人、行人、乘车人以及与道路交通活动有关的单位和个人，都应当遵守本法。</br><br>第三条　道路交通安全工作，应当遵循依法管理、方便群众的原则，保障道路交通有序、安全、畅通。</br><br>第四条　各级人民政府应当保障道路交通安全管理工作与经济建设和社会发展相适应。</br><br>县级以上地方各级人民政府应当适应道路交通发展的需要，依据道路交通安全法律、法规和国家有关政策，制定道路交通安全管理规划，并组织实施。</br><br>第五条　国务院公安部门负责全国道路交通安全管理工作。县级以上地方各级人民政府公安机关交通管理部门负责本行政区域内的道路交通安全管理工作。</br><br>县级以上各级人民政府交通、建设管理部门依据各自职责，负责有关的道路交通工作。</br><br>第六条　各级人民政府应当经常进行道路交通安全教育，提高公民的道路交通安全意识。</br><br>公安机关交通管理部门及其交通警察执行职务时，应当加强道路交通安全法律、法规的宣传，并模范遵守道路交通安全法律、法规。</br><br>机关、部队、企业事业单位、社会团体以及其他组织，应当对本单位的人员进行道路交通安全教育。</br><br>教育行政部门、学校应当将道路交通安全教育纳入法制教育的内容。</br><br>新闻、出版、广播、电视等有关单位，有进行道路交通安全教育的义务。</br><br>第七条　对道路交通安全管理工作，应当加强科学研究，推广、使用先进的管理方法、技术、设备。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 603:
			str = "<p><br>为了规范道路交通事故处理程序，保障公安机关交通管理部门依法履行职责，保护道路交通事故当事人的合法权益，根据《中华人民共和国道路交通安全法》及其实施条例等有关法律、法规，制定了本规定，2008年7月11日公安部部长办公会议通过，2009年1月1日颁布实施。</br></p> ";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 604:
			str = "<p><br>第一条为了规范道路交通事故处理程序，保障公安机关交通管理部门依法履行职责，保护道路交通事故当事人的合法权益，根据《中华人民共和国道路交通安全法》及其实施条例等有关法律、法规，制定本规定。</br><br>第二条公安机关交通管理部门处理道路交通事故，应当遵循公正、公开、便民、效率的原则。</br><br>第三条交通警察处理道路交通事故，应当取得相应等级的处理道路交通事故资格。</br></p> ";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 605:
			str = "<p>    2013年1月1日起，新修订的《机动车驾驶证申领和使用规定》(公安部123号令)正式实施，市民对其中涉及的交通违法行为记分调整很关注。</p><p>最新处罚条文：</p><p>    酒后驾驶分两种：酒精含量达到20mg/100ml但不足80mg/100ml，属于饮酒驾驶;酒精含量达到或超过80mg/100ml，属于醉酒驾驶。目前，饮酒驾驶属于违法行为，醉酒驾驶属于犯罪行为。</p><p>    饮酒驾驶机动车辆，罚款1000元—2000元、记12分并暂扣驾照6个月;饮酒驾驶营运机动车，罚款5000元，记12分，处以15日以下拘留，并且5年内不得重新获得驾照。</p><p>    醉酒驾驶机动车辆，吊销驾照，5年内不得重新获取驾照，经过判决后处以拘役，并处罚金;醉酒驾驶营运机动车辆，吊销驾照，10年内不得重新获取驾照，终生不得驾驶营运车辆，经过判决后处以拘役，并处罚金。</p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 701:
			str = "<p>操作要求</p>\n<p><br>用低速按规定的行驶路线﹐不停车地一次90度急转弯通过直角。如转不过弯﹐可借助一次倒车﹐但要被扣10分。</br></p><p>评分标准</p><p><br>1车轮触压突出点﹐不及格</br><br>2车轮触压路牙一次﹐扣20分</br><br>3借助倒车﹐扣10分</br></p><p>操作方法</p><p><br>进入直角弯前，先打右转向灯，车头快到直角弯入口路口路宽1/2处时，向右打轮1圈半，尽量让车身靠右贴，远离路牙。而靠右贴的合适位置，以路边出现在车头右起1/3处为宜，此时把轮回正，车往前直行，前方视野中左侧反光镜下沿即将看到路牙时，向左连续打轮2圈(速度不要过猛)。当路边出现在车头1/2处时，向右回轮1圈，打右转向灯。当车与路快正时，再向右回轮1圈，使出直角弯。</br></p><p>驾驶技巧</p><p><br>1一挡通过﹐不加油门;</br><br>2驶入考场时尽量紧贴右侧路牙;</br><br>3正前方的路牙一旦被引擎盖挡住视线，就要向左猛打方向，直至打到底;</br><br>4看到车头对准出口，就迅速将方向回正。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 702:
			str = "<p>驾驶技巧</p>\n<p><br>系统发出会车指令后，考生要正确判断会车地点，会车有危险时，控制车速，提前避让，调整会车地点，会车时与对方车辆保持安全间距。会车的要领是：刹车减速，靠右行驶。</br></p><p>评判标准</p>\n<p><br>①在没有中心隔离设施或中心线的道路上会车时，不减速靠右行驶，与行人、他车未保持安全距离，不合格；</br><br>②会车困难时不让行，不合格；</br><br>③横向安全间距判断差，紧急转向避让相对方向过来车，不合格；</br><br>④夜间考试，按照规定使用远近灯交替示意。</br></p><p>操作要求</p>\n<p><br>系统发出超车指令后，要在一分钟之内完成超车动作。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 703:
			str = "<p>前期准备</p>\n<p><br>1首先：调整座椅位置，使踩踏离合器和脚刹、油门时较舒服（尤其是便于使离合器处于半离合状态）。</br><br>2其次：调整左后视镜，使车身占后视镜约1/5（即在左后视镜中可以看见车左后下方）。</br><br>3然后：调整右后视镜，使车身占后视镜约1/5（即使右后门把手在右后视镜的左下方）。</br></p><p>评判标准</p>\n<p><br>1车辆入库停止后，车身出线的，不合格。</br><br>2中途停车的，不合格。</br><br>3行驶中轮胎压车道边线的，扣10分。</br></p><p>操作步骤</p>\n<p><br>1车身在车库的左侧，右轮沿着库左边线行驶，车轮距左边线50cm（从座位上看，桑塔纳一般在车后侧机盖1/3的位置压线），行驶到库上边线以前50cm处停车。</br><br>2准备倒车时，开左转向灯，然后开始倒车。从右面车窗中柱看2号角，2号角离中柱5cm时，向右打1.5圈（打死）。</br><br>3当左侧后视镜出现4号点（即车库的右后角）时向左回轮1.5圈（即回正方向），继续倒车。</br><br>4从左后视镜看左后车轮，马上快碰到边缘线时，向左打死方向到车证（两侧后视镜观察到车身与车库边线平行）停车。</br><br>5出库：开左转向灯，方向盘不用回，挂1档，档看到机盖最右侧越过左前库1号点时，向右回轮1.5圈，继续前进进到右侧雨刷最后一节库左线时，向右打一圈，车正回1圈，向前行驶。关转向灯，考试结束</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 704:
			str = "<p>一、考试目的</p>\n<p><br>1在规定场地内驾驶机动车完成考试项目的情况;</br><br>2对机动车驾驶技能掌握的情况;</br><br>3对机动车空间位置判断的能力。</br></p><p>二、考试项目</p>\n<p><br>倒车入库、坡道起步和定点停车、侧方停车、曲线行驶、直角转弯五项必考。考试细则调整更加严格一些倒桩考试：倒库过程中不能停车 一次倒入 右侧起点倒入车库 然后开到左边倒入车库 开到起点 取消移库侧方停车：操作需一次倒入 中间不可停车直角转弯：不能压线 需一次完成不能停车定点停车坡道起步：停车原规定30公分 现10公分左右 坡道起步按原有正常操作大型客车、牵引车、城市公交车、中型客车、大型货车准驾车型考试项目不得少于6项。大型客车、城市公交车必考项目：桩考、坡道定点停车和起步、直角转弯、通过单边桥、通过连续障碍;牵引车准驾车型必考项目：桩考、坡道定点停车和起步、曲线行驶、直角转弯、限速通过限宽门;中型客车、大型货车准驾车型必考项目：桩考、坡道定点停车和起步、侧方停车、通过单边桥、通过连续障碍。其他考试项目随机选取。小型汽车、小型自动挡汽车、低速载货汽车、普通三轮摩托车、普通二轮摩托车准驾车型考试项目不得少于4项。小型汽车、低速载货汽车必考项目：桩考、坡道定点停车和起步、侧方停车;小型自动挡汽车必考项目：桩考、侧方停车;普通三轮摩托车、普通二轮摩托车准驾车型必考项目：桩考、坡道定点停车和起步、通过单边 桥。其他考试项目随机选取。应当先进行桩考。桩考未出现扣分情形的，补考或者重新预约考试时可以不再进行桩考。</br><br>其他准驾车型的考试项目，由省级公安机关交通管理部门确定。</br></p><p>三、合格标准</p>\n<p><br>满分为100分，设定不合格、减20分、减10分、减5分的项目评判标准。符合下列规定的，考试合格：</br><br>1报考大型客车、牵引车、城市公交车、中型客车、大型货车准驾车型，成绩达到90分的;</br><br>2报考其他准驾车型成绩达到80分的。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 705:
			str = "<p>前期准备</p>\n<p><br>1首先：调整座椅位置，使踩踏离合器和脚刹、油门时较舒服（尤其是便于使离合器处于半离合状态）。</br><br>2其次：调整左后视镜，使车身占后视镜约1/5（即在左后视镜中可以看见车左后下方）。</br><br>3然后：调整右后视镜，使车身占后视镜约1/5（即使右后门把手在右后视镜的左下方）。</br></p><p>评判标准</p>\n<p><br>1车辆入库停止后，车身出线的，不合格。</br><br>2中途停车的，不合格。</br><br>3行驶中轮胎压车道边线的，扣10分。</br></p><p>操作步骤</p>\n<p><br>1车身在车库的左侧，右轮沿着库左边线行驶，车轮距左边线50cm（从座位上看，桑塔纳一般在车后侧机盖1/3的位置压线），行驶到库上边线以前50cm处停车。</br><br>2准备倒车时，开左转向灯，然后开始倒车。从右面车窗中柱看2号角，2号角离中柱5cm时，向右打1.5圈（打死）。</br><br>3当左侧后视镜出现4号点（即车库的右后角）时向左回轮1.5圈（即回正方向），继续倒车。</br><br>4从左后视镜看左后车轮，马上快碰到边缘线时，向左打死方向到车证（两侧后视镜观察到车身与车库边线平行）停车。</br><br>5出库：开左转向灯，方向盘不用回，挂1档，档看到机盖最右侧越过左前库1号点时，向右回轮1.5圈，继续前进进到右侧雨刷最后一节库左线时，向右打一圈，车正回1圈，向前行驶。关转向灯，考试结束</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		case 706:
			str = "<p>驾驶技巧</p>\n<p><br>系统发出会车指令后，考生要正确判断会车地点，会车有危险时，控制车速，提前避让，调整会车地点，会车时与对方车辆保持安全间距。会车的要领是：刹车减速，靠右行驶。</br></p><p>评判标准</p>\n<p><br>①在没有中心隔离设施或中心线的道路上会车时，不减速靠右行驶，与行人、他车未保持安全距离，不合格；</br><br>②会车困难时不让行，不合格；</br><br>③横向安全间距判断差，紧急转向避让相对方向过来车，不合格；</br><br>④夜间考试，按照规定使用远近灯交替示意。</br></p><p>操作要求</p>\n<p><br>系统发出超车指令后，要在一分钟之内完成超车动作。</br></p>";
			tvContent.setText(Html.fromHtml(str));
			break;
		default:
			break;
		}
	}

}
