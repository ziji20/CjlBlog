import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.chainsaw.Main;
import org.springframework.web.context.ContextLoader;

import com.cjl.util.ObtainPictureName;
import java.util.Timer;  
import java.util.TimerTask;  
public class Test {
	
	
	public static void main(String[] args) {
		  // 第一种方法：设定指定任务task在指定时间time执行 schedule(TimerTask task, Date time)
		    Timer timer = new Timer();
		    timer.schedule(new TimerTask() {
		      public void run() {
		        System.out.println("-------设定要指定任务--------");
		      }
		    }, 10000);// 设定指定的时间time,此处为2000毫秒
	}
/*
	public static void main(String[] args) {
//		String html="<html>";
//		System.out.println(StringEscapeUtils.escapeHtml(html));
		ObtainPictureName.ImgName("<div id=\"id1\" class=\"section\"> \r\n" + 
				" <h1>1&nbsp;&nbsp;&nbsp;概述</h1> \r\n" + 
				" <p>在存在账号体系的信息系统中，对身份的鉴定是非常重要的事情。</p> \r\n" + 
				" <p>随着移动互联网时代到来，客户端的类型越来越多， 逐渐出现了&nbsp;一个服务器，N个客户端的格局&nbsp;。</p> \r\n" + 
				" <p><img src=\"http:/static/userImages/2018/04/12/7cd35df0-dc70-448b-aa7c-a3cfe64979bc.png\" alt=\"\"></p> \r\n" + 
				" <p>不同的客户端产生了不同的用户使用场景，这些场景：</p> \r\n" + 
				" <ol class=\"arabic simple\"> \r\n" + 
				"  <li>有不同的环境安全威胁</li> \r\n" + 
				"  <li>不同的会话生存周期</li> \r\n" + 
				"  <li>不同的用户权限控制体系</li> \r\n" + 
				"  <li>不同级别的接口调用方式</li> \r\n" + 
				" </ol> \r\n" + 
				" <p>综上所述，它们的身份认证方式也存在一定的区别。</p> \r\n" + 
				" <p>本文将使用一定的篇幅对这些场景进行一些分析和梳理工作。</p> \r\n" + 
				"</div> \r\n" + 
				"<div id=\"id2\" class=\"section\"> \r\n" + 
				" <h1><a name=\"t1\"></a>2&nbsp;&nbsp;&nbsp;使用场景</h1> \r\n" + 
				" <p>下面是一些在IT服务常见的一些使用场景:</p> \r\n" + 
				" <ol class=\"arabic simple\"> \r\n" + 
				"  <li>用户在web浏览器端登录系统,使用系统服务</li> \r\n" + 
				"  <li>用户在手机端（Android/iOS）登录系统,使用系统服务</li> \r\n" + 
				"  <li>用户使用开放接口登录系统,调用系统服务</li> \r\n" + 
				"  <li>用户在PC处理登录状态时通过手机扫码授权手机登录（使用得比较少）</li> \r\n" + 
				"  <li>用户在手机处理登录状态进通过手机扫码授权PC进行登录（比较常见）</li> \r\n" + 
				" </ol> \r\n" + 
				" <p>通过对场景的细分,得到如下不同的认证token类别:</p> \r\n" + 
				" <ol class=\"arabic\"> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     原始账号密码类别\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <ul class=\"first last simple\"> \r\n" + 
				"      <li>用户名和密码</li> \r\n" + 
				"      <li>API应用ID/KEY</li> \r\n" + 
				"     </ul> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     会话ID类别\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <ul class=\"first last simple\"> \r\n" + 
				"      <li>浏览器端token</li> \r\n" + 
				"      <li>移动端token</li> \r\n" + 
				"      <li>API应用token</li> \r\n" + 
				"     </ul> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     接口调用类别\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <ul class=\"first last simple\"> \r\n" + 
				"      <li>接口访问token</li> \r\n" + 
				"     </ul> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     身份授权类别\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <ul class=\"first last simple\"> \r\n" + 
				"      <li>PC和移动端相互授权的token</li> \r\n" + 
				"     </ul> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				" </ol>\r\n" + 
				"</div> \r\n" + 
				"<div id=\"id3\" class=\"section\"> \r\n" + 
				" <h1><a name=\"t2\"></a>3&nbsp;&nbsp;&nbsp;token的类别</h1> \r\n" + 
				" <p>不同场景的token进行如下几个维度的对比:</p> \r\n" + 
				" <p>天然属性&nbsp;对比:</p> \r\n" + 
				" <ol class=\"arabic\"> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     使用成本\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <p class=\"first\">本认证方式在使用的时候,造成的不便性。比如:</p> \r\n" + 
				"     <ul class=\"last simple\"> \r\n" + 
				"      <li>账号密码需要用户打开页面然后逐个键入</li> \r\n" + 
				"      <li>二维码需要用户掏出手机进行扫码操作</li> \r\n" + 
				"     </ul> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     变化成本\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <p class=\"first\">本认证方式,token发生变化时,用户需要做出的相应更改的成本:</p> \r\n" + 
				"     <ul class=\"last simple\"> \r\n" + 
				"      <li>用户名和密码发生变化时,用户需要额外记忆和重新键入新密码</li> \r\n" + 
				"      <li>API应用ID/KEY发生变化时,第三方应用需要重新在代码中修改并部署</li> \r\n" + 
				"      <li>授权二维码发生变化时,需要用户重新打开手机应用进行扫码</li> \r\n" + 
				"     </ul> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				"  <li> <p class=\"first\">环境风险</p> \r\n" + 
				"   <blockquote> \r\n" + 
				"    <ul class=\"simple\"> \r\n" + 
				"     <li>被偷窥的风险</li> \r\n" + 
				"     <li>被抓包的风险</li> \r\n" + 
				"     <li>被伪造的风险</li> \r\n" + 
				"    </ul> \r\n" + 
				"   </blockquote> </li> \r\n" + 
				" </ol> \r\n" + 
				" <p>可调控属性&nbsp;对比:</p> \r\n" + 
				" <ol class=\"arabic\"> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     使用频率\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <ul class=\"first last simple\"> \r\n" + 
				"      <li>在网路中传送的频率</li> \r\n" + 
				"     </ul> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     有效时间\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <ul class=\"first last simple\"> \r\n" + 
				"      <li>此token从创建到终结的生存时间</li> \r\n" + 
				"     </ul> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				" </ol> \r\n" + 
				" <p>最终的目标:安全和影响。</p> \r\n" + 
				" <p>安全和隐私性主要体现在:</p> \r\n" + 
				" <ul class=\"simple\"> \r\n" + 
				"  <li>token 不容易被窃取和盗用（通过对传送频率控制）</li> \r\n" + 
				"  <li>token 即使被窃取,产生的影响也是可控的（通过对有效时间控制）</li> \r\n" + 
				" </ul> \r\n" + 
				" <p>关于隐私及隐私破坏后的后果,有如下的基本结论:</p> \r\n" + 
				" <ol class=\"arabic simple\"> \r\n" + 
				"  <li>曝光频率高的容易被截获</li> \r\n" + 
				"  <li>生存周期长的在被截获后产生的影响更严重和深远</li> \r\n" + 
				" </ol> \r\n" + 
				" <p>遵守如下原则:</p> \r\n" + 
				" <ol class=\"arabic simple\"> \r\n" + 
				"  <li>变化成本高的token不要轻易变化</li> \r\n" + 
				"  <li>不轻易变化的token要减少曝光频率（网络传输次数）</li> \r\n" + 
				"  <li>曝光频率高的token的生存周期要尽量短</li> \r\n" + 
				" </ol> \r\n" + 
				" <p>将各类token的固有特点及可控属性进行调控后,&nbsp;对每个指标进行量化评分（1~5分），我们可以得到如下的对比表：</p> \r\n" + 
				" <img src=\"http:/static/userImages/2018/04/12/903106d7-c8b1-460b-82af-d43bec10cbd0.png\" alt=\"\">\r\n" + 
				" <br> \r\n" + 
				" <p>备注:</p> \r\n" + 
				" <ul class=\"simple\"> \r\n" + 
				"  <li>user_name/passwd&nbsp;和&nbsp;app_id/app_key&nbsp;是等价的效果</li> \r\n" + 
				" </ul> \r\n" + 
				"</div> \r\n" + 
				"<div id=\"id4\" class=\"section\"> \r\n" + 
				" <h1><a name=\"t3\"></a>4&nbsp;&nbsp;&nbsp;token的层级关系</h1> \r\n" + 
				" <p>参考上一节的对比表，可以很容易对这些不同用途的token进行分层，主要可以分为4层：</p> \r\n" + 
				" <ol class=\"arabic\"> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     密码层\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <p class=\"first last\">最传统的用户和系统之间约定的数字身份认证方式</p> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     会话层\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <p class=\"first last\">用户登录后的会话生命周期的会话认证</p> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     调用层\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <p class=\"first last\">用户在会话期间对应用程序接口的调用认证</p> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				"  <li>\r\n" + 
				"   <dl class=\"first docutils\">\r\n" + 
				"    <dt>\r\n" + 
				"     应用层\r\n" + 
				"    </dt>\r\n" + 
				"    <dd> \r\n" + 
				"     <p class=\"first last\">用户获取了接口访问调用权限后的一些场景或者身份认证应用</p> \r\n" + 
				"    </dd>\r\n" + 
				"   </dl></li> \r\n" + 
				" </ol>\r\n" + 
				"</div> \r\n" + 
				"<div class=\"section\">\r\n" + 
				" token的分层图如下：\r\n" + 
				"</div> \r\n" + 
				"<div class=\"section\">\r\n" + 
				" <img src=\"http:/static/userImages/2018/04/12/17fb43cd-39d4-4364-a2e7-a9fa647fd5c7.png\" alt=\"\">\r\n" + 
				" <br> \r\n" + 
				" <p>在一个多客户端的信息系统里面,这些token的产生及应用的内在联系如下:</p> \r\n" + 
				" <ol class=\"arabic simple\"> \r\n" + 
				"  <li>用户输入用户名和用户口令进行一次性认证</li> \r\n" + 
				"  <li>在&nbsp;不同&nbsp;的终端里面生成拥有&nbsp;不同&nbsp;生命周期的会话token</li> \r\n" + 
				"  <li>客户端会话token从服务端交换生命周期短但曝光&nbsp;频繁&nbsp;的接口访问token</li> \r\n" + 
				"  <li>会话token可以生成和刷新延长&nbsp;access_token&nbsp;的生存时间</li> \r\n" + 
				"  <li>access_token可以生成生存周期最短的用于授权的二维码的token</li> \r\n" + 
				" </ol> \r\n" + 
				" <p>使用如上的架构有如下的好处：</p> \r\n" + 
				" <ol class=\"arabic simple\"> \r\n" + 
				"  <li>良好的统一性。可以解决不同平台上认证token的生存周期的&nbsp;归一化&nbsp;问题</li> \r\n" + 
				"  <li>良好的解耦性。核心接口调用服务器的认证 access_token 可以完成独立的实现和部署</li> \r\n" + 
				"  <li>良好的层次性。不同平台的可以有完全不同的用户权限控制系统，这个控制可以在&nbsp;会话层&nbsp;中各平台解决掉</li> \r\n" + 
				" </ol> \r\n" + 
				" <div id=\"id5\" class=\"section\"> \r\n" + 
				"  <h2><a name=\"t4\"></a>4.1&nbsp;&nbsp;&nbsp;账号密码</h2> \r\n" + 
				"  <p>广义的&nbsp;账号/密码&nbsp;有如下的呈现方式:</p> \r\n" + 
				"  <ol class=\"arabic simple\"> \r\n" + 
				"   <li>传统的注册用户名和密码</li> \r\n" + 
				"   <li>应用程序的app_id/app_key</li> \r\n" + 
				"  </ol> \r\n" + 
				"  <p>它们的特点如下：</p> \r\n" + 
				"  <ol class=\"arabic\"> \r\n" + 
				"   <li>\r\n" + 
				"    <dl class=\"first docutils\">\r\n" + 
				"     <dt>\r\n" + 
				"      会有特别的意义\r\n" + 
				"     </dt>\r\n" + 
				"     <dd> \r\n" + 
				"      <p class=\"first last\">比如：用户自己为了方便记忆，会设置有一定含义的账号和密码。</p> \r\n" + 
				"     </dd>\r\n" + 
				"    </dl></li> \r\n" + 
				"   <li>\r\n" + 
				"    <dl class=\"first docutils\">\r\n" + 
				"     <dt>\r\n" + 
				"      不常修改\r\n" + 
				"     </dt>\r\n" + 
				"     <dd> \r\n" + 
				"      <p class=\"first last\">账号密码对用户有特别含义，一般没有特殊情况不会愿意修改。 而app_id/app_key则会写在应用程序中，修改会意味着重新发布上线的成本</p> \r\n" + 
				"     </dd>\r\n" + 
				"    </dl></li> \r\n" + 
				"   <li>\r\n" + 
				"    <dl class=\"first docutils\">\r\n" + 
				"     <dt>\r\n" + 
				"      一旦泄露影响深远\r\n" + 
				"     </dt>\r\n" + 
				"     <dd> \r\n" + 
				"      <p class=\"first last\">正因为不常修改，只要泄露了基本相当于用户的网络身份被泄露，而且只要没被察觉这种身份盗用就会一直存在</p> \r\n" + 
				"     </dd>\r\n" + 
				"    </dl></li> \r\n" + 
				"  </ol> \r\n" + 
				"  <p>所以在认证系统中应该尽量减少传输的机会，避免泄露。</p> \r\n" + 
				" </div> \r\n" + 
				" <div id=\"id6\" class=\"section\"> \r\n" + 
				"  <h2><a name=\"t5\"></a>4.2&nbsp;&nbsp;&nbsp;客户端会话token</h2> \r\n" + 
				"  <p>功能：充当着session的角色，不同的客户端有不同的生命周期。</p> \r\n" + 
				"  <p>使用步骤：</p> \r\n" + 
				"  <ol class=\"arabic simple\"> \r\n" + 
				"   <li>用户使用账号密码，换取会话token</li> \r\n" + 
				"  </ol> \r\n" + 
				"  <p>不同的平台的token有不同的特点。</p> \r\n" + 
				"  <p>Web平台生存周期短</p> \r\n" + 
				"  <p>主要原因：</p> \r\n" + 
				"  <ol class=\"arabic\"> \r\n" + 
				"   <li>\r\n" + 
				"    <dl class=\"first docutils\">\r\n" + 
				"     <dt>\r\n" + 
				"      环境安全性\r\n" + 
				"     </dt>\r\n" + 
				"     <dd> \r\n" + 
				"      <p class=\"first last\">由于web登录环境一般很可能是公共环境，被他人盗取的风险值较大</p> \r\n" + 
				"     </dd>\r\n" + 
				"    </dl></li> \r\n" + 
				"   <li>\r\n" + 
				"    <dl class=\"first docutils\">\r\n" + 
				"     <dt>\r\n" + 
				"      输入便捷性\r\n" + 
				"     </dt>\r\n" + 
				"     <dd> \r\n" + 
				"      <p class=\"first last\">在PC上使用键盘输入会比较便捷</p> \r\n" + 
				"     </dd>\r\n" + 
				"    </dl></li> \r\n" + 
				"  </ol> \r\n" + 
				"  <p>移动端生存周期长</p> \r\n" + 
				"  <p>主要原因：</p> \r\n" + 
				"  <ol class=\"arabic\"> \r\n" + 
				"   <li>\r\n" + 
				"    <dl class=\"first docutils\">\r\n" + 
				"     <dt>\r\n" + 
				"      环境安全性\r\n" + 
				"     </dt>\r\n" + 
				"     <dd> \r\n" + 
				"      <p class=\"first last\">移动端平台是个人用户极其私密的平台，它人接触的机会不大</p> \r\n" + 
				"     </dd>\r\n" + 
				"    </dl></li> \r\n" + 
				"   <li>\r\n" + 
				"    <dl class=\"first docutils\">\r\n" + 
				"     <dt>\r\n" + 
				"      输入便捷性\r\n" + 
				"     </dt>\r\n" + 
				"     <dd> \r\n" + 
				"      <p class=\"first last\">在移动端上使用手指在小屏幕上触摸输入体验差，输入成本高</p> \r\n" + 
				"     </dd>\r\n" + 
				"    </dl></li> \r\n" + 
				"  </ol>\r\n" + 
				" </div> \r\n" + 
				" <div id=\"access-token\" class=\"section\"> \r\n" + 
				"  <h2><a name=\"t6\"></a>4.3&nbsp;&nbsp;&nbsp;access_token</h2> \r\n" + 
				"  <p>功能：服务端应用程序api接口访问和调用的凭证。</p> \r\n" + 
				"  <p>使用步骤：</p> \r\n" + 
				"  <ol class=\"arabic simple\"> \r\n" + 
				"   <li>使用具有较长生命周期的会话token来换取此接口访问token。</li> \r\n" + 
				"  </ol> \r\n" + 
				"  <p>其曝光频率直接和接口调用频率有关，属于高频使用的凭证。 为了照顾到隐私性，尽量减少其生命周期，即使被截取了，也不至于产生严重的后果。</p> \r\n" + 
				"  <p>注意：在客户端token之下还加上一个access_token， 主要是为了让具有不同生命周期的客户端token最后在调用api的时候， 能够具有统一的认证方式。</p> \r\n" + 
				" </div> \r\n" + 
				" <div id=\"pam-token\" class=\"section\"> \r\n" + 
				"  <h2><a name=\"t7\"></a>4.4&nbsp;&nbsp;&nbsp;pam_token</h2> \r\n" + 
				"  <p>功能：由已经登录和认证的PC端生成的二维码的原始串号（Pc Auth Mobile）。</p> \r\n" + 
				"  <p>主要步骤如下：</p> \r\n" + 
				"  <ol class=\"arabic simple\"> \r\n" + 
				"   <li>PC上用户已经完成认证，登录了系统</li> \r\n" + 
				"   <li>PC端生成一组和此用户相关联的pam_token</li> \r\n" + 
				"   <li>PC端将此pam_token的使用链接生成二维码</li> \r\n" + 
				"   <li>移动端扫码后，请求服务器，并和用户信息关联</li> \r\n" + 
				"   <li>移动端获取refresh_token(长时效的会话)</li> \r\n" + 
				"   <li>根据 refresh_token 获取 access_token</li> \r\n" + 
				"   <li>完成正常的接口调用工作</li> \r\n" + 
				"  </ol> \r\n" + 
				"  <p>备注:</p> \r\n" + 
				"  <ul class=\"simple\"> \r\n" + 
				"   <li>生存周期为2分钟,2分钟后过期删除</li> \r\n" + 
				"   <li>没有被使用时,每1分钟变一次</li> \r\n" + 
				"   <li>被使用后,立刻删除掉</li> \r\n" + 
				"   <li>此种认证模式一般不会被使用到</li> \r\n" + 
				"  </ul> \r\n" + 
				" </div> \r\n" + 
				" <div id=\"map-token\" class=\"section\"> \r\n" + 
				"  <h2><a name=\"t8\"></a>4.5&nbsp;&nbsp;&nbsp;map_token</h2> \r\n" + 
				"  <p>功能：由已经登录的移动app来扫码认证PC端系统，并完成PC端系统的登录（Mobile Auth Pc）。</p> \r\n" + 
				"  <p>主要步骤：</p> \r\n" + 
				"  <ol class=\"arabic simple\"> \r\n" + 
				"   <li>移动端完成用户身份的认证登录app</li> \r\n" + 
				"   <li>未登录的PC生成匿名的&nbsp;map_token</li> \r\n" + 
				"   <li>移动端扫码后在db中生成&nbsp;map_token&nbsp;和用户关联（完成签名）</li> \r\n" + 
				"   <li>db同时针对此用户生成&nbsp;web_token</li> \r\n" + 
				"   <li>PC端一直以&nbsp;map_token&nbsp;为参数查找此命名用户的&nbsp;web_token</li> \r\n" + 
				"   <li>PC端根据&nbsp;web_token&nbsp;去获取&nbsp;access_token</li> \r\n" + 
				"   <li>后续正常的调用接口调用工作</li> \r\n" + 
				"  </ol> \r\n" + 
				"  <p>备注:</p> \r\n" + 
				"  <ul class=\"simple\"> \r\n" + 
				"   <li>生存周期为2分钟,2分钟后过期删除</li> \r\n" + 
				"   <li>没有被使用时,每1分钟变一次</li> \r\n" + 
				"   <li>被使用后,立刻删除掉</li> \r\n" + 
				"  </ul> \r\n" + 
				" </div> \r\n" + 
				"</div> \r\n" + 
				"<div id=\"id7\" class=\"section\"> \r\n" + 
				" <h1><a name=\"t9\"></a>5&nbsp;&nbsp;&nbsp;小结与展望</h1> \r\n" + 
				" <p>本文所设计的基于token的身份认证系统，主要解决了如下的问题：</p> \r\n" + 
				" <ol class=\"arabic simple\"> \r\n" + 
				"  <li>token的分类问题</li> \r\n" + 
				"  <li>token的隐私性参数设置问题</li> \r\n" + 
				"  <li>token的使用场景问题</li> \r\n" + 
				"  <li>不同生命周期的token分层转化关系</li> \r\n" + 
				" </ol> \r\n" + 
				" <p>本文中提到的设计方法，在&nbsp;应用层&nbsp;中可以适用于且不限于如下场景中：</p> \r\n" + 
				" <ol class=\"arabic simple\">\r\n" + 
				"  <ol class=\"arabic simple\"> \r\n" + 
				"   <li>\r\n" + 
				"    <ol class=\"arabic simple\"> \r\n" + 
				"     <li>用户登录</li> \r\n" + 
				"     <li>有时效的优惠券发放</li> \r\n" + 
				"     <li>有时效的邀请码发放</li> \r\n" + 
				"     <li>有时效的二维码授权</li> \r\n" + 
				"     <li>具有时效&nbsp;手机/邮件&nbsp;验证码</li> \r\n" + 
				"     <li>多个不同平台调用同一套API接口</li> \r\n" + 
				"     <li>多个平台使用同一个身份认证中心</li> \r\n" + 
				"    </ol></li> \r\n" + 
				"  </ol>\r\n" + 
				" </ol> \r\n" + 
				" <p style=\"text-align: left\">文章并非个人原创，只是觉得还不错就收藏分享一下。支持原创：https://blog.csdn.net/u010265681/article/details/76651766</p> \r\n" + 
				"</div>");
		
		System.out.println(System.getProperty("user.dir"));
		System.out.println(Main.class.getResource(""));
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
	}*/
}

