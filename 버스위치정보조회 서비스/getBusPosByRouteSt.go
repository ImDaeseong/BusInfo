// getBusPosByRouteSt
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

type getBusPosByRouteStTag struct {
	busType    string //차량유형 (0:일반버스, 1:저상버스, 2:굴절버스)
	congetion  string //혼잡도 (3 : 여유, 4 : 보통, 5 : 혼잡, 6 : 매우혼잡)
	dataTm     string //제공시간
	isFullFlag string //만차여부(0 : 만차아님, 1: 만차)
	lastStnId  string //최종정류장ID
	plainNo    string //차량번호
	posX       string //맵매칭X좌표 (GRS80)
	posY       string //맵매칭Y좌표 (GRS80)
	routeId    string //노선 ID
	sectDist   string //구간옵셋거리(Km)
	sectOrd    string //구간순번
	sectionId  string //구간 ID
	stopFlag   string //정류소도착여부 (0:운행중, 1:도착)
	tmX        string //맵매칭X좌표 (WGS84)
	tmY        string //맵매칭Y좌표 (WGS84)
	vehId      string //버스 ID
}

var mgetBusRouteListTagItem = make(map[int]getBusPosByRouteStTag)

func findJobTag(html string) string {
	first := strings.Index(html, ">")
	end := strings.LastIndex(html, "<")
	return string(html[first+1 : end])
}

func getBusPosByRouteSt() bool {

	defaulturl := "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRouteSt?"
	busRouteId := fmt.Sprintf("busRouteId=%d", 117900003) //금천03
	startOrd := fmt.Sprintf("&startOrd=%d", 1)
	endOrd := fmt.Sprintf("&endOrd=%d", 10)
	ServiceKey := fmt.Sprintf("&ServiceKey=%s", API_Key)
	sUrl := fmt.Sprintf("%s%s%s%s%s", defaulturl, busRouteId, startOrd, endOrd, ServiceKey)

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
	var busType = regexp.MustCompile(`<busType>([\w\W]+?)</busType>`)
	var congetion = regexp.MustCompile(`<congetion>([\w\W]+?)</congetion>`)
	var dataTm = regexp.MustCompile(`<dataTm>([\w\W]+?)</dataTm>`)
	var isFullFlag = regexp.MustCompile(`<isFullFlag>([\w\W]+?)</isFullFlag>`)
	var lastStnId = regexp.MustCompile(`<lastStnId>([\w\W]+?)</lastStnId>`)
	var plainNo = regexp.MustCompile(`<plainNo>([\w\W]+?)</plainNo>`)
	var posX = regexp.MustCompile(`<posX>([\w\W]+?)</posX>`)
	var posY = regexp.MustCompile(`<posY>([\w\W]+?)</posY>`)
	var routeId = regexp.MustCompile(`<routeId>([\w\W]+?)</routeId>`)
	var sectDist = regexp.MustCompile(`<sectDist>([\w\W]+?)</sectDist>`)
	var sectOrd = regexp.MustCompile(`<sectOrd>([\w\W]+?)</sectOrd>`)
	var sectionId = regexp.MustCompile(`<sectionId>([\w\W]+?)</sectionId>`)
	var stopFlag = regexp.MustCompile(`<stopFlag>([\w\W]+?)</stopFlag>`)
	var tmX = regexp.MustCompile(`<tmX>([\w\W]+?)</tmX>`)
	var tmY = regexp.MustCompile(`<tmY>([\w\W]+?)</tmY>`)
	var vehId = regexp.MustCompile(`<vehId>([\w\W]+?)</vehId>`)
	var itemList = regexp.MustCompile(`<itemList>([\w\W]+?)</itemList>`)
	match := itemList.FindAllString(string(body), -1)
	if match != nil {

		for _, val := range match {

			thumb1 := busType.FindAllString(val, -1)
			if len(thumb1) == 0 {
				continue
			}

			thumb2 := congetion.FindAllString(val, -1)
			if len(thumb2) == 0 {
				continue
			}

			thumb3 := dataTm.FindAllString(val, -1)
			if len(thumb3) == 0 {
				continue
			}

			thumb4 := isFullFlag.FindAllString(val, -1)
			if len(thumb4) == 0 {
				continue
			}

			thumb5 := lastStnId.FindAllString(val, -1)
			if len(thumb5) == 0 {
				continue
			}

			thumb6 := plainNo.FindAllString(val, -1)
			if len(thumb6) == 0 {
				continue
			}

			thumb7 := posX.FindAllString(val, -1)
			if len(thumb7) == 0 {
				continue
			}

			thumb8 := posY.FindAllString(val, -1)
			if len(thumb8) == 0 {
				continue
			}

			thumb9 := routeId.FindAllString(val, -1)
			if len(thumb9) == 0 {
				continue
			}

			thumb10 := sectDist.FindAllString(val, -1)
			if len(thumb10) == 0 {
				continue
			}

			thumb11 := sectOrd.FindAllString(val, -1)
			if len(thumb11) == 0 {
				continue
			}

			thumb12 := sectionId.FindAllString(val, -1)
			if len(thumb12) == 0 {
				continue
			}

			thumb13 := stopFlag.FindAllString(val, -1)
			if len(thumb13) == 0 {
				continue
			}

			thumb14 := tmX.FindAllString(val, -1)
			if len(thumb14) == 0 {
				continue
			}

			thumb15 := tmY.FindAllString(val, -1)
			if len(thumb15) == 0 {
				continue
			}

			thumb16 := vehId.FindAllString(val, -1)
			if len(thumb16) == 0 {
				continue
			}

			mgetBusRouteListTagItem[Index] = getBusPosByRouteStTag{thumb1[0], thumb2[0],
				thumb3[0], thumb4[0], thumb5[0], thumb6[0], thumb7[0],
				thumb8[0], thumb9[0], thumb10[0], thumb11[0], thumb12[0], thumb13[0],
				thumb14[0], thumb15[0], thumb16[0],
			}
			Index++
		}
	}

	return true
}

func main() {

	getBusPosByRouteSt()
	for _, val := range mgetBusRouteListTagItem {

		sResult := fmt.Sprintf("%s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s\n %s",
			findJobTag(val.busType),
			findJobTag(val.congetion),
			findJobTag(val.dataTm),
			findJobTag(val.isFullFlag),
			findJobTag(val.lastStnId),
			findJobTag(val.plainNo),
			findJobTag(val.posX),
			findJobTag(val.posY),
			findJobTag(val.routeId),
			findJobTag(val.sectDist),
			findJobTag(val.sectOrd),
			findJobTag(val.sectionId),
			findJobTag(val.stopFlag),
			findJobTag(val.tmX),
			findJobTag(val.tmY),
			findJobTag(val.vehId))
		fmt.Println(sResult)
	}
}
