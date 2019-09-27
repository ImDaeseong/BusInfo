// 경유노선 전체 정류소 도착예정정보를 조회한다
package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"regexp"
	"strings"
)

const API_Key = "api key"

type getArrInfoByRouteAllTag struct {
	arrmsg1      string //첫번째 도착예정 버스의 도착정보메시지
	arrmsg2      string //두번째 도착예정 버스의 도착정보메시지
	arsId        string //정류소 고유번호
	avgCf1       string //첫번째 도착예정 버스의 이동평균 보정계수
	avgCf2       string //두번째 도착예정 버스의 이동평균 보정계수
	brdrde_Num1  string //첫번째 도착예정 버스의 버스내부 제공용 현재 뒷차 인원
	brdrde_Num2  string //두번째 도착예정 버스의 버스내부 제공용 현재 뒷차 인원
	brerde_Div1  string //첫번째 도착예정 버스의 버스내부 제공용 현재 뒷차 구분
	brerde_Div2  string //두번째 도착예정 버스의 버스내부 제공용 현재 뒷차 구분
	busRouteId   string //노선ID
	busType1     string //첫번째도착예정버스의 차량유형 (0:일반버스, 1:저상버스, 2:굴절버스)
	busType2     string //두번째도착예정버스의 차량유형 (0:일반버스, 1:저상버스, 2:굴절버스)
	deTourAt     string
	dir          string //방향
	expCf1       string //첫번째 도착예정 버스의 지수평활 보정계수
	expCf2       string //두번째 도착예정 버스의 지수평활 보정계수
	exps1        string //첫번째 도착예정 버스의 지수평활 도착예정시간(초)
	exps2        string //두번째 도착예정 버스의 지수평활 도착예정시간(초)
	firstTm      string //첫차시간
	full1        string //첫번째 도착예정 버스의 만차여부
	full2        string //두번째 도착예정 버스의 만차여부
	goal1        string //첫번째 도착예정 버스의 종점 도착예정시간(초)
	goal2        string //두번째 도착예정 버스의 종점 도착예정시간(초)
	isArrive1    string //첫번째도착예정버스의 최종 정류소 도착출발여부 (0:운행중, 1:도착)
	isArrive2    string //두번째도착예정버스의 최종 정류소 도착출발여부 (0:운행중, 1:도착)
	isLast1      string //첫번째도착예정버스의 막차여부 (0:막차아님, 1:막차)
	isLast2      string //두번째도착예정버스의 막차여부 (0:막차아님, 1:막차)
	kalCf1       string //첫번째 도착예정 버스의 기타1평균 보정계수
	kalCf2       string
	kals1        string //첫번째 도착예정 버스의 기타1 도착예정시간(초)
	kals2        string //두번째 도착예정 버스의 기타1 도착예정시간(초)
	lastTm       string //막차시간
	mkTm         string //제공시각
	namin2Sec1   string //첫번째 도착예정 버스의 2번째 주요정류소 예정여행시간
	namin2Sec2   string //두번째 도착예정 버스의 2번째 주요정류소 예정여행시간
	neuCf1       string //첫번째 도착예정 버스의 기타2평균 보정계수
	neuCf2       string //두번째 도착예정 버스의 기타2평균 보정계수
	neus1        string //첫번째 도착예정 버스의 기타2 도착예정시간(초)
	neus2        string //두번째 도착예정 버스의 기타2 도착예정시간(초)
	nextBus      string //막차운행여부 (N:막차아님, Y:막차)
	nmain2Ord1   string //첫번째 도착예정 버스의 2번째 주요정류소 순번
	nmain2Ord2   string //두번째 도착예정 버스의 2번째 주요정류소 순번
	nmain2Stnid1 string //첫번째 도착예정 버스의 2번째 주요정류소 ID
	nmain2Stnid2 string //두번째 도착예정 버스의 2번째 주요정류소 ID
	nmain3Ord1   string //첫번째 도착예정 버스의 3번째 주요정류소 순번
	nmain3Ord2   string //두번째 도착예정 버스의 3번째 주요정류소 순번
	nmain3Sec1   string //첫번째 도착예정 버스의 3번째 주요정류소 예정여행시간
	nmain3Sec2   string //두번째 도착예정 버스의 3번째 주요정류소 예정여행시간
	nmain3Stnid1 string //첫번째 도착예정 버스의 3번째 주요정류소 ID
	nmain3Stnid2 string //두번째 도착예정 버스의 3번째 주요정류소 ID
	nmainOrd1    string //첫번째 도착예정 버스의 1번째 주요정류소 순번
	nmainOrd2    string //두번째 도착예정 버스의 1번째 주요정류소 순번
	nmainSec1    string //첫번째 도착예정 버스의 1번째 주요정류소 예정여행시간
	nmainSec2    string //두번째 도착예정 버스의 1번째 주요정류소 예정여행시간
	nmainStnid1  string //첫번째 도착예정 버스의 1번째 주요정류소 ID
	nmainStnid2  string //두번째 도착예정 버스의 1번째 주요정류소 ID
	nstnId1      string //첫번째 도착예정 버스의 다음정류소 ID
	nstnId2      string //두번째 도착예정 버스의 다음정류소 ID
	nstnOrd1     string //첫번째 도착예정 버스의다음 정류소 순번
	nstnOrd2     string //두번째 도착예정 버스의다음 정류소 순번
	nstnSec1     string //첫번째 도착예정 버스의 다음 정류소 예정여행시간
	nstnSec2     string //두번째 도착예정 버스의 다음 정류소 예정여행시간
	nstnSpd1     string //첫번째 도착예정 버스의 다음 정류소 예정여행시간
	nstnSpd2     string //두번째 도착예정 버스의 다음 정류소 예정여행시간
	plainNo1     string //첫번째도착예정차량번호
	plainNo2     string //두번째도착예정차량번호
	rerdie_Div1  string //첫번째 도착예정 버스의 버스내부 제공용 현재 재차 구분
	rerdie_Div2  string //두번째 도착예정 버스의 버스내부 제공용 현재 재차 구분
	reride_Num1  string //첫번째 도착예정 버스의 버스내부 제공용 현재 재차 인원
	reride_Num2  string //두번째 도착예정 버스의 버스내부 제공용 현재 재차 인원
	routeType    string //노선유형 (1:공항, 2:마을, 3:간선, 4:지선, 5:순환, 6:광역, 7:인천, 8:경기, 9:폐지, 0:공용)
	rtNm         string //노선명
	sectOrd1     string //첫번째도착예정버스의 현재구간 순번
	sectOrd2     string //두번째도착예정버스의 현재구간 순번
	stId         string //정류소 ID
	stNm         string //정류소명
	staOrd       string //요청정류소순번
	term         string //배차간격 (분)
	traSpd1      string //첫번째도착예정버스의 여행속도 (Km/h)
	traSpd2      string //두번째도착예정버스의 여행속도
	traTime1     string //첫번째도착예정버스의 여행시간 (분)
	traTime2     string //두번째도착예정버스의 여행시간
	vehId1       string //첫번째도착예정버스ID
	vehId2       string //두번째 도착예정버스ID
}

var mgetBusRouteListTagItem = make(map[int]getArrInfoByRouteAllTag)

func findJobTag(html string) string {
	first := strings.Index(html, ">")
	end := strings.LastIndex(html, "<")
	return string(html[first+1 : end])
}

func getArrInfoByRouteAll() bool {

	defaulturl := "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll?"
	busRouteId := fmt.Sprintf("busRouteId=%d", 117900003) //금천03
	ServiceKey := fmt.Sprintf("&ServiceKey=%s", API_Key)
	sUrl := fmt.Sprintf("%s%s%s", defaulturl, busRouteId, ServiceKey)

	res, err := http.Get(sUrl)
	if err != nil {
		log.Println(err)
		return false
	}
	defer res.Body.Close()

	body, err := ioutil.ReadAll(res.Body)
	if err != nil {
		log.Println(err)
		return false
	}

	var Index int = 0
	var arrmsg1 = regexp.MustCompile(`<arrmsg1>([\w\W]+?)</arrmsg1>`)
	var arrmsg2 = regexp.MustCompile(`<arrmsg2>([\w\W]+?)</arrmsg2>`)
	var arsId = regexp.MustCompile(`<arsId>([\w\W]+?)</arsId>`)
	var avgCf1 = regexp.MustCompile(`<avgCf1>([\w\W]+?)</avgCf1>`)
	var avgCf2 = regexp.MustCompile(`<avgCf2>([\w\W]+?)</avgCf2>`)
	var brdrde_Num1 = regexp.MustCompile(`<brdrde_Num1>([\w\W]+?)</brdrde_Num1>`)
	var brdrde_Num2 = regexp.MustCompile(`<brdrde_Num2>([\w\W]+?)</brdrde_Num2>`)
	var brerde_Div1 = regexp.MustCompile(`<brerde_Div1>([\w\W]+?)</brerde_Div1>`)
	var brerde_Div2 = regexp.MustCompile(`<brerde_Div2>([\w\W]+?)</brerde_Div2>`)
	var busRouteId1 = regexp.MustCompile(`<busRouteId>([\w\W]+?)</busRouteId>`)
	var busType1 = regexp.MustCompile(`<busType1>([\w\W]+?)</busType1>`)
	var busType2 = regexp.MustCompile(`<busType2>([\w\W]+?)</busType2>`)
	var deTourAt = regexp.MustCompile(`<deTourAt>([\w\W]+?)</deTourAt>`)
	var dir = regexp.MustCompile(`<dir>([\w\W]+?)</dir>`)
	var expCf1 = regexp.MustCompile(`<expCf1>([\w\W]+?)</expCf1>`)
	var expCf2 = regexp.MustCompile(`<expCf2>([\w\W]+?)</expCf2>`)
	var exps1 = regexp.MustCompile(`<exps1>([\w\W]+?)</exps1>`)
	var exps2 = regexp.MustCompile(`<exps2>([\w\W]+?)</exps2>`)
	var firstTm = regexp.MustCompile(`<firstTm>([\w\W]+?)</firstTm>`)
	var full1 = regexp.MustCompile(`<full1>([\w\W]+?)</full1>`)
	var full2 = regexp.MustCompile(`<full2>([\w\W]+?)</full2>`)
	var goal1 = regexp.MustCompile(`<goal1>([\w\W]+?)</goal1>`)
	var goal2 = regexp.MustCompile(`<goal2>([\w\W]+?)</goal2>`)
	var isArrive1 = regexp.MustCompile(`<isArrive1>([\w\W]+?)</isArrive1>`)
	var isArrive2 = regexp.MustCompile(`<isArrive2>([\w\W]+?)</isArrive2>`)
	var isLast1 = regexp.MustCompile(`<isLast1>([\w\W]+?)</isLast1>`)
	var isLast2 = regexp.MustCompile(`<isLast2>([\w\W]+?)</isLast2>`)
	var kalCf1 = regexp.MustCompile(`<kalCf1>([\w\W]+?)</kalCf1>`)
	var kalCf2 = regexp.MustCompile(`<kalCf2>([\w\W]+?)</kalCf2>`)
	var kals1 = regexp.MustCompile(`<kals1>([\w\W]+?)</kals1>`)
	var kals2 = regexp.MustCompile(`<kals2>([\w\W]+?)</kals2>`)
	var lastTm = regexp.MustCompile(`<lastTm>([\w\W]+?)</lastTm>`)
	var mkTm = regexp.MustCompile(`<mkTm>([\w\W]+?)</mkTm>`)
	var namin2Sec1 = regexp.MustCompile(`<namin2Sec1>([\w\W]+?)</namin2Sec1>`)
	var namin2Sec2 = regexp.MustCompile(`<namin2Sec2>([\w\W]+?)</namin2Sec2>`)
	var neuCf1 = regexp.MustCompile(`<neuCf1>([\w\W]+?)</neuCf1>`)
	var neuCf2 = regexp.MustCompile(`<neuCf2>([\w\W]+?)</neuCf2>`)
	var neus1 = regexp.MustCompile(`<neus1>([\w\W]+?)</neus1>`)
	var neus2 = regexp.MustCompile(`<neus2>([\w\W]+?)</neus2>`)
	var nextBus = regexp.MustCompile(`<nextBus>([\w\W]+?)</nextBus>`)
	var nmain2Ord1 = regexp.MustCompile(`<nmain2Ord1>([\w\W]+?)</nmain2Ord1>`)
	var nmain2Ord2 = regexp.MustCompile(`<nmain2Ord2>([\w\W]+?)</nmain2Ord2>`)
	var nmain2Stnid1 = regexp.MustCompile(`<nmain2Stnid1>([\w\W]+?)</nmain2Stnid1>`)
	var nmain2Stnid2 = regexp.MustCompile(`<nmain2Stnid2>([\w\W]+?)</nmain2Stnid2>`)
	var nmain3Ord1 = regexp.MustCompile(`<nmain3Ord1>([\w\W]+?)</nmain3Ord1>`)
	var nmain3Ord2 = regexp.MustCompile(`<nmain3Ord2>([\w\W]+?)</nmain3Ord2>`)
	var nmain3Sec1 = regexp.MustCompile(`<nmain3Sec1>([\w\W]+?)</nmain3Sec1>`)
	var nmain3Sec2 = regexp.MustCompile(`<nmain3Sec2>([\w\W]+?)</nmain3Sec2>`)
	var nmain3Stnid1 = regexp.MustCompile(`<nmain3Stnid1>([\w\W]+?)</nmain3Stnid1>`)
	var nmain3Stnid2 = regexp.MustCompile(`<nmain3Stnid2>([\w\W]+?)</nmain3Stnid2>`)
	var nmainOrd1 = regexp.MustCompile(`<nmainOrd1>([\w\W]+?)</nmainOrd1>`)
	var nmainOrd2 = regexp.MustCompile(`<nmainOrd2>([\w\W]+?)</nmainOrd2>`)
	var nmainSec1 = regexp.MustCompile(`<nmainSec1>([\w\W]+?)</nmainSec1>`)
	var nmainSec2 = regexp.MustCompile(`<nmainSec2>([\w\W]+?)</nmainSec2>`)
	var nmainStnid1 = regexp.MustCompile(`<nmainStnid1>([\w\W]+?)</nmainStnid1>`)
	var nmainStnid2 = regexp.MustCompile(`<nmainStnid2>([\w\W]+?)</nmainStnid2>`)
	var nstnId1 = regexp.MustCompile(`<nstnId1>([\w\W]+?)</nstnId1>`)
	var nstnId2 = regexp.MustCompile(`<nstnId2>([\w\W]+?)</nstnId2>`)
	var nstnOrd1 = regexp.MustCompile(`<nstnOrd1>([\w\W]+?)</nstnOrd1>`)
	var nstnOrd2 = regexp.MustCompile(`<nstnOrd2>([\w\W]+?)</nstnOrd2>`)
	var nstnSec1 = regexp.MustCompile(`<nstnSec1>([\w\W]+?)</nstnSec1>`)
	var nstnSec2 = regexp.MustCompile(`<nstnSec2>([\w\W]+?)</nstnSec2>`)
	var nstnSpd1 = regexp.MustCompile(`<nstnSpd1>([\w\W]+?)</nstnSpd1>`)
	var nstnSpd2 = regexp.MustCompile(`<nstnSpd2>([\w\W]+?)</nstnSpd2>`)
	var plainNo1 = regexp.MustCompile(`<plainNo1>([\w\W]+?)</plainNo1>`)
	var plainNo2 = regexp.MustCompile(`<plainNo2>([\w\W]+?)</plainNo2>`)
	var rerdie_Div1 = regexp.MustCompile(`<rerdie_Div1>([\w\W]+?)</rerdie_Div1>`)
	var rerdie_Div2 = regexp.MustCompile(`<rerdie_Div2>([\w\W]+?)</rerdie_Div2>`)
	var reride_Num1 = regexp.MustCompile(`<reride_Num1>([\w\W]+?)</reride_Num1>`)
	var reride_Num2 = regexp.MustCompile(`<reride_Num2>([\w\W]+?)</reride_Num2>`)
	var routeType = regexp.MustCompile(`<routeType>([\w\W]+?)</routeType>`)
	var rtNm = regexp.MustCompile(`<rtNm>([\w\W]+?)</rtNm>`)
	var sectOrd1 = regexp.MustCompile(`<sectOrd1>([\w\W]+?)</sectOrd1>`)
	var sectOrd2 = regexp.MustCompile(`<sectOrd2>([\w\W]+?)</sectOrd2>`)
	var stId = regexp.MustCompile(`<stId>([\w\W]+?)</stId>`)
	var stNm = regexp.MustCompile(`<stNm>([\w\W]+?)</stNm>`)
	var staOrd = regexp.MustCompile(`<staOrd>([\w\W]+?)</staOrd>`)
	var term = regexp.MustCompile(`<term>([\w\W]+?)</term>`)
	var traSpd1 = regexp.MustCompile(`<traSpd1>([\w\W]+?)</traSpd1>`)
	var traSpd2 = regexp.MustCompile(`<traSpd2>([\w\W]+?)</traSpd2>`)
	var traTime1 = regexp.MustCompile(`<traTime1>([\w\W]+?)</traTime1>`)
	var traTime2 = regexp.MustCompile(`<traTime2>([\w\W]+?)</traTime2>`)
	var vehId1 = regexp.MustCompile(`<vehId1>([\w\W]+?)</vehId1>`)
	var vehId2 = regexp.MustCompile(`<vehId2>([\w\W]+?)</vehId2>`)
	var itemList = regexp.MustCompile(`<itemList>([\w\W]+?)</itemList>`)
	match := itemList.FindAllString(string(body), -1)
	if match != nil {

		for _, val := range match {

			thumb1 := arrmsg1.FindAllString(val, -1)
			if len(thumb1) == 0 {
				continue
			}

			thumb2 := arrmsg2.FindAllString(val, -1)
			if len(thumb2) == 0 {
				continue
			}

			thumb3 := arsId.FindAllString(val, -1)
			if len(thumb3) == 0 {
				continue
			}

			thumb4 := avgCf1.FindAllString(val, -1)
			if len(thumb4) == 0 {
				continue
			}

			thumb5 := avgCf2.FindAllString(val, -1)
			if len(thumb5) == 0 {
				continue
			}

			thumb6 := brdrde_Num1.FindAllString(val, -1)
			if len(thumb6) == 0 {
				continue
			}

			thumb7 := brdrde_Num2.FindAllString(val, -1)
			if len(thumb7) == 0 {
				continue
			}

			thumb8 := brerde_Div1.FindAllString(val, -1)
			if len(thumb8) == 0 {
				continue
			}

			thumb9 := brerde_Div2.FindAllString(val, -1)
			if len(thumb9) == 0 {
				continue
			}

			thumb10 := busRouteId1.FindAllString(val, -1)
			if len(thumb10) == 0 {
				continue
			}

			thumb11 := busType1.FindAllString(val, -1)
			if len(thumb11) == 0 {
				continue
			}

			thumb12 := busType2.FindAllString(val, -1)
			if len(thumb12) == 0 {
				continue
			}

			thumb13 := deTourAt.FindAllString(val, -1)
			if len(thumb13) == 0 {
				continue
			}

			thumb14 := dir.FindAllString(val, -1)
			if len(thumb14) == 0 {
				continue
			}

			thumb15 := expCf1.FindAllString(val, -1)
			if len(thumb15) == 0 {
				continue
			}

			thumb16 := expCf2.FindAllString(val, -1)
			if len(thumb16) == 0 {
				continue
			}

			thumb17 := exps1.FindAllString(val, -1)
			if len(thumb17) == 0 {
				continue
			}

			thumb18 := exps2.FindAllString(val, -1)
			if len(thumb18) == 0 {
				continue
			}

			thumb19 := firstTm.FindAllString(val, -1)
			if len(thumb19) == 0 {
				continue
			}

			thumb20 := full1.FindAllString(val, -1)
			if len(thumb20) == 0 {
				continue
			}

			thumb21 := full2.FindAllString(val, -1)
			if len(thumb21) == 0 {
				continue
			}

			thumb22 := goal1.FindAllString(val, -1)
			if len(thumb22) == 0 {
				continue
			}

			thumb23 := goal2.FindAllString(val, -1)
			if len(thumb23) == 0 {
				continue
			}

			thumb24 := isArrive1.FindAllString(val, -1)
			if len(thumb24) == 0 {
				continue
			}

			thumb25 := isArrive2.FindAllString(val, -1)
			if len(thumb25) == 0 {
				continue
			}

			thumb26 := isLast1.FindAllString(val, -1)
			if len(thumb26) == 0 {
				continue
			}

			thumb27 := isLast2.FindAllString(val, -1)
			if len(thumb27) == 0 {
				continue
			}

			thumb28 := kalCf1.FindAllString(val, -1)
			if len(thumb28) == 0 {
				continue
			}

			thumb29 := kalCf2.FindAllString(val, -1)
			if len(thumb29) == 0 {
				continue
			}

			thumb30 := kals1.FindAllString(val, -1)
			if len(thumb30) == 0 {
				continue
			}

			thumb31 := kals2.FindAllString(val, -1)
			if len(thumb31) == 0 {
				continue
			}

			thumb32 := lastTm.FindAllString(val, -1)
			if len(thumb32) == 0 {
				continue
			}

			thumb33 := mkTm.FindAllString(val, -1)
			if len(thumb33) == 0 {
				continue
			}

			thumb34 := namin2Sec1.FindAllString(val, -1)
			if len(thumb34) == 0 {
				continue
			}

			thumb35 := namin2Sec2.FindAllString(val, -1)
			if len(thumb35) == 0 {
				continue
			}

			thumb36 := neuCf1.FindAllString(val, -1)
			if len(thumb36) == 0 {
				continue
			}

			thumb37 := neuCf2.FindAllString(val, -1)
			if len(thumb37) == 0 {
				continue
			}

			thumb38 := neus1.FindAllString(val, -1)
			if len(thumb38) == 0 {
				continue
			}

			thumb39 := neus2.FindAllString(val, -1)
			if len(thumb39) == 0 {
				continue
			}

			thumb40 := nextBus.FindAllString(val, -1)
			if len(thumb40) == 0 {
				continue
			}

			thumb41 := nmain2Ord1.FindAllString(val, -1)
			if len(thumb41) == 0 {
				continue
			}

			thumb42 := nmain2Ord2.FindAllString(val, -1)
			if len(thumb42) == 0 {
				continue
			}

			thumb43 := nmain2Stnid1.FindAllString(val, -1)
			if len(thumb43) == 0 {
				continue
			}

			thumb44 := nmain2Stnid2.FindAllString(val, -1)
			if len(thumb44) == 0 {
				continue
			}

			thumb45 := nmain3Ord1.FindAllString(val, -1)
			if len(thumb45) == 0 {
				continue
			}

			thumb46 := nmain3Ord2.FindAllString(val, -1)
			if len(thumb46) == 0 {
				continue
			}

			thumb47 := nmain3Sec1.FindAllString(val, -1)
			if len(thumb47) == 0 {
				continue
			}

			thumb48 := nmain3Sec2.FindAllString(val, -1)
			if len(thumb48) == 0 {
				continue
			}

			thumb49 := nmain3Stnid1.FindAllString(val, -1)
			if len(thumb49) == 0 {
				continue
			}

			thumb50 := nmain3Stnid2.FindAllString(val, -1)
			if len(thumb50) == 0 {
				continue
			}

			thumb51 := nmainOrd1.FindAllString(val, -1)
			if len(thumb51) == 0 {
				continue
			}

			thumb52 := nmainOrd2.FindAllString(val, -1)
			if len(thumb52) == 0 {
				continue
			}

			thumb53 := nmainSec1.FindAllString(val, -1)
			if len(thumb53) == 0 {
				continue
			}

			thumb54 := nmainSec2.FindAllString(val, -1)
			if len(thumb54) == 0 {
				continue
			}

			thumb55 := nmainStnid1.FindAllString(val, -1)
			if len(thumb55) == 0 {
				continue
			}

			thumb56 := nmainStnid2.FindAllString(val, -1)
			if len(thumb56) == 0 {
				continue
			}

			thumb57 := nstnId1.FindAllString(val, -1)
			if len(thumb57) == 0 {
				continue
			}

			thumb58 := nstnId2.FindAllString(val, -1)
			if len(thumb58) == 0 {
				continue
			}

			thumb59 := nstnOrd1.FindAllString(val, -1)
			if len(thumb59) == 0 {
				continue
			}

			thumb60 := nstnOrd2.FindAllString(val, -1)
			if len(thumb60) == 0 {
				continue
			}

			thumb61 := nstnSec1.FindAllString(val, -1)
			if len(thumb61) == 0 {
				continue
			}

			thumb62 := nstnSec2.FindAllString(val, -1)
			if len(thumb62) == 0 {
				continue
			}

			thumb63 := nstnSpd1.FindAllString(val, -1)
			if len(thumb63) == 0 {
				continue
			}

			thumb64 := nstnSpd2.FindAllString(val, -1)
			if len(thumb64) == 0 {
				continue
			}

			thumb65 := plainNo1.FindAllString(val, -1)
			if len(thumb65) == 0 {
				continue
			}

			thumb66 := plainNo2.FindAllString(val, -1)
			if len(thumb66) == 0 {
				continue
			}

			thumb67 := rerdie_Div1.FindAllString(val, -1)
			if len(thumb67) == 0 {
				continue
			}

			thumb68 := rerdie_Div2.FindAllString(val, -1)
			if len(thumb68) == 0 {
				continue
			}

			thumb69 := reride_Num1.FindAllString(val, -1)
			if len(thumb69) == 0 {
				continue
			}

			thumb70 := reride_Num2.FindAllString(val, -1)
			if len(thumb70) == 0 {
				continue
			}

			thumb71 := routeType.FindAllString(val, -1)
			if len(thumb71) == 0 {
				continue
			}

			thumb72 := rtNm.FindAllString(val, -1)
			if len(thumb72) == 0 {
				continue
			}

			thumb73 := sectOrd1.FindAllString(val, -1)
			if len(thumb73) == 0 {
				continue
			}

			thumb74 := sectOrd2.FindAllString(val, -1)
			if len(thumb74) == 0 {
				continue
			}

			thumb75 := stId.FindAllString(val, -1)
			if len(thumb75) == 0 {
				continue
			}

			thumb76 := stNm.FindAllString(val, -1)
			if len(thumb76) == 0 {
				continue
			}

			thumb77 := staOrd.FindAllString(val, -1)
			if len(thumb77) == 0 {
				continue
			}

			thumb78 := term.FindAllString(val, -1)
			if len(thumb78) == 0 {
				continue
			}

			thumb79 := traSpd1.FindAllString(val, -1)
			if len(thumb79) == 0 {
				continue
			}

			thumb80 := traSpd2.FindAllString(val, -1)
			if len(thumb80) == 0 {
				continue
			}

			thumb81 := traTime1.FindAllString(val, -1)
			if len(thumb81) == 0 {
				continue
			}

			thumb82 := traTime2.FindAllString(val, -1)
			if len(thumb82) == 0 {
				continue
			}

			thumb83 := vehId1.FindAllString(val, -1)
			if len(thumb83) == 0 {
				continue
			}
			thumb84 := vehId2.FindAllString(val, -1)
			if len(thumb84) == 0 {
				continue
			}

			mgetBusRouteListTagItem[Index] = getArrInfoByRouteAllTag{thumb1[0], thumb2[0],
				thumb3[0], thumb4[0], thumb5[0], thumb6[0], thumb7[0],
				thumb8[0], thumb9[0], thumb10[0], thumb11[0], thumb12[0], thumb13[0],
				thumb14[0], thumb15[0], thumb16[0], thumb17[0], thumb18[0], thumb19[0],
				thumb20[0], thumb21[0], thumb22[0], thumb23[0], thumb24[0], thumb25[0],
				thumb26[0], thumb27[0], thumb28[0], thumb29[0], thumb30[0], thumb31[0],
				thumb32[0], thumb33[0], thumb34[0], thumb35[0], thumb36[0], thumb37[0],
				thumb38[0], thumb39[0], thumb40[0], thumb41[0], thumb42[0], thumb43[0],
				thumb44[0], thumb45[0], thumb46[0], thumb47[0], thumb48[0], thumb49[0],
				thumb50[0], thumb51[0], thumb52[0], thumb53[0], thumb54[0], thumb55[0],
				thumb56[0], thumb57[0], thumb58[0], thumb59[0], thumb60[0], thumb61[0],
				thumb62[0], thumb63[0], thumb64[0], thumb65[0], thumb66[0], thumb67[0],
				thumb68[0], thumb69[0], thumb70[0], thumb71[0], thumb72[0], thumb73[0],
				thumb74[0], thumb75[0], thumb76[0], thumb77[0], thumb78[0], thumb79[0],
				thumb80[0], thumb81[0], thumb82[0], thumb83[0], thumb84[0],
			}

			Index++
		}
	}

	return true
}

func main() {

	getArrInfoByRouteAll()

	for _, val := range mgetBusRouteListTagItem {

		sResult := fmt.Sprintf(
			"================================================="+
				"%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n"+
				"%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n"+
				"%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n"+
				"%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n"+
				"%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n"+
				"%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n"+
				"%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n"+
				"%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n"+
				"%s\n %s\n %s\n %s\n"+
				"=================================================",
			findJobTag(val.arrmsg1),
			findJobTag(val.arrmsg2),
			findJobTag(val.arsId),
			findJobTag(val.avgCf1),
			findJobTag(val.avgCf2),
			findJobTag(val.brdrde_Num1),
			findJobTag(val.brdrde_Num2),
			findJobTag(val.brerde_Div1),
			findJobTag(val.brerde_Div2),
			findJobTag(val.busRouteId),
			findJobTag(val.busType1),
			findJobTag(val.busType2),
			findJobTag(val.deTourAt),
			findJobTag(val.dir),
			findJobTag(val.expCf1),
			findJobTag(val.expCf2),
			findJobTag(val.exps1),
			findJobTag(val.exps2),
			findJobTag(val.firstTm),
			findJobTag(val.full1),
			findJobTag(val.full2),
			findJobTag(val.goal1),
			findJobTag(val.goal2),
			findJobTag(val.isArrive1),
			findJobTag(val.isArrive2),
			findJobTag(val.isLast1),
			findJobTag(val.isLast2),
			findJobTag(val.kalCf1),
			findJobTag(val.kalCf2),
			findJobTag(val.kals1),
			findJobTag(val.kals2),
			findJobTag(val.lastTm),
			findJobTag(val.mkTm),
			findJobTag(val.namin2Sec1),
			findJobTag(val.namin2Sec2),
			findJobTag(val.neuCf1),
			findJobTag(val.neuCf2),
			findJobTag(val.neus1),
			findJobTag(val.neus2),
			findJobTag(val.nextBus),
			findJobTag(val.nmain2Ord1),
			findJobTag(val.nmain2Ord2),
			findJobTag(val.nmain2Stnid1),
			findJobTag(val.nmain2Stnid2),
			findJobTag(val.nmain3Ord1),
			findJobTag(val.nmain3Ord2),
			findJobTag(val.nmain3Sec1),
			findJobTag(val.nmain3Sec2),
			findJobTag(val.nmain3Stnid1),
			findJobTag(val.nmain3Stnid2),
			findJobTag(val.nmainOrd1),
			findJobTag(val.nmainOrd2),
			findJobTag(val.nmainSec1),
			findJobTag(val.nmainSec2),
			findJobTag(val.nmainStnid1),
			findJobTag(val.nmainStnid2),
			findJobTag(val.nstnId1),
			findJobTag(val.nstnId2),
			findJobTag(val.nstnOrd1),
			findJobTag(val.nstnOrd2),
			findJobTag(val.nstnSec1),
			findJobTag(val.nstnSec2),
			findJobTag(val.nstnSpd1),
			findJobTag(val.nstnSpd2),
			findJobTag(val.plainNo1),
			findJobTag(val.plainNo2),
			findJobTag(val.rerdie_Div1),
			findJobTag(val.rerdie_Div2),
			findJobTag(val.reride_Num1),
			findJobTag(val.reride_Num2),
			findJobTag(val.routeType),
			findJobTag(val.rtNm),
			findJobTag(val.sectOrd1),
			findJobTag(val.sectOrd2),
			findJobTag(val.stId),
			findJobTag(val.stNm),
			findJobTag(val.staOrd),
			findJobTag(val.term),
			findJobTag(val.traSpd1),
			findJobTag(val.traSpd2),
			findJobTag(val.traTime1),
			findJobTag(val.traTime2),
			findJobTag(val.vehId1),
			findJobTag(val.vehId2))
		fmt.Println(sResult)
	}

}
