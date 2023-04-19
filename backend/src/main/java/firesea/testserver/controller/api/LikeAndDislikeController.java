package firesea.testserver.controller.api;

import firesea.testserver.domain.basic.DefaultRes;
import firesea.testserver.service.LikeAndDislikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LikeAndDislikeController {

    private final LikeAndDislikeService likeAndDislikeService;

    //좋아요 싫어요
    @GetMapping("/api/likeTm")
    public DefaultRes likeTm(@RequestParam int id, HttpServletRequest request) throws UnknownHostException {
        //요청 ip 가져오기
        String ip = getClientIp(request);
        int likes = likeAndDislikeService.likeTm(id, ip);

        return DefaultRes.res( 20019, "[접속 ip]" + ip, likes );
    }

    @GetMapping("/api/dislikeTm")
    public DefaultRes dislikeTm(@RequestParam int id, HttpServletRequest request) throws UnknownHostException {
        //요청 ip 가져오기
        String ip = getClientIp(request);
        int dislikes = likeAndDislikeService.dislikeTm(id, ip);

        return DefaultRes.res( 20020, "[접속 ip]" + ip, dislikes);
    }

    @GetMapping("/api/countTmLikes")
    public DefaultRes countTmLikes(@RequestParam int id) {

        int cnt = likeAndDislikeService.countTmLikes(id);

        return DefaultRes.res(20011, id + "번 글의 좋아요 갯수 조회", cnt);
    }

    @GetMapping("/api/countTmDislikes")
    public DefaultRes countTmDislikes(@RequestParam int id) {

        int cnt = likeAndDislikeService.countTmDislikes(id);

        return DefaultRes.res(20012, id + "번 글의 싫어요 갯수 조회", cnt);
    }




    public String getClientIp(HttpServletRequest request) throws UnknownHostException {
        String ip = request.getHeader("X-Forwarded-For");
        log.info("ip = {}", ip);

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("ip1 = {}", ip);

        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            log.info("ip2 = {}", ip);
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            log.info("ip3 = {}", ip);
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            log.info("ip4 = {}", ip);
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            log.info("ip5 = {}", ip);
            ip = request.getHeader("X-FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            log.info("ip6 = {}", ip);
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            log.info("ip7 = {}", ip);
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            log.info("ip8 = {}", ip);
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            log.info("ip9 = {}", ip);
            ip = request.getRemoteAddr();
        }

        if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")){
            InetAddress clientAddress = InetAddress.getLocalHost();
            ip = clientAddress.getHostName()+"/"+ clientAddress.getHostAddress();
            log.info("ip10 = {}", ip);
        }

        return ip;
    }


}
