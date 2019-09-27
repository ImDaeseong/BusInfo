// getBusPosByRtid
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

type getBusPosByRtidTag struct {
	busType      string //차량유형 (0:일반버스, 1:저상버스, 2:굴절버스)
	congetion    string //혼잡도 (3 : 여유, 4 : 보통, 5 : 혼잡, 6 : 매우혼잡)
	dataTm       string //제공시간
	fullSectDist string //정류소간 거리
	gpsX         string //맵매칭X좌표 (WGS84)
	gpsY         string //맵매칭Y좌표 (WGS84)
	isFullFlag   string //만차여부(0 : 만차아님, 1: 만차)
	islastyn     string //막차여부(0 : 막차아님, 1: 막차)
	isrunyn      string //해당차량 운행여부 (0: 운행종료, 1: 운행)
	lastStTm     string //종점도착소요시간
	lastStnId    string //최종정류장 ID
	nextStId     string //다음 주요정류소ID
	nextStTm     string //다음 주요정류소 도착예정시간
	plainNo      string //차량번호
	posX         string //맵매칭X좌표 (GRS80)
	posY         string //맵매칭Y좌표 (GRS80)
	rtDist       string //노선옵셋거리 (Km)
	sectDist     string //정류장 구간이동거리
	sectOrd      string //구간순번
	sectionId    string //구간ID
	stopFlag     string //정류소도착여부 (0:운행중, 1:도착)
	trnstnid     string //회차지 정류소ID
	vehId        string //버스 ID
}

var mgetBusRouteListTagItem = make(map[int]getBusPosByRtidTag)

func findJobTag(html string) string {
	first := strings.Index(html, ">")
	end := strings.LastIndex(html, "<")
	return string(html[first+1 : end])
}

func getBusPosByRtid() bool {

	defaulturl := "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?"
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
	var busType = regexp.MustCompile(`<busType>([\w\W]+?)</busType>`)
	var congetion = regexp.MustCompile(`<congetion>([\w\W]+?)</congetion>`)
	var dataTm = regexp.MustCompile(`<dataTm>([\w\W]+?)</dataTm>`)
	var fullSectDist = regexp.MustCompile(`<fullSectDist>([\w\W]+?)</fullSectDist>`)
	var gpsX = regexp.MustCompile(`<gpsX>([\w\W]+?)</gpsX>`)
	var gpsY = regexp.MustCompile(`<gpsY>([\w\W]+?)</gpsY>`)
	var isFullFlag = regexp.MustCompile(`<isFullFlag>([\w\W]+?)</isFullFlag>`)
	var islastyn = regexp.MustCompile(`<islastyn>([\w\W]+?)</islastyn>`)
	var isrunyn = regexp.MustCompile(`<isrunyn>([\w\W]+?)</isrunyn>`)
	var lastStTm = regexp.MustCompile(`<lastStTm>([\w\W]+?)</lastStTm>`)
	var lastStnId = regexp.MustCompile(`<lastStnId>([\w\W]+?)</lastStnId>`)
	var nextStId = regexp.MustCompile(`<nextStId>([\w\W]+?)</nextStId>`)
	var nextStTm = regexp.MustCompile(`<nextStTm>([\w\W]+?)</nextStTm>`)
	var plainNo = regexp.MustCompile(`<plainNo>([\w\W]+?)</plainNo>`)
	var posX = regexp.MustCompile(`<posX>([\w\W]+?)</posX>`)
	var posY = regexp.MustCompile(`<posY>([\w\W]+?)</posY>`)
	var rtDist = regexp.MustCompile(`<rtDist>([\w\W]+?)</rtDist>`)
	var sectDist = regexp.MustCompile(`<sectDist>([\w\W]+?)</sectDist>`)
	var sectOrd = regexp.MustCompile(`<sectOrd>([\w\W]+?)</sectOrd>`)
	var sectionId = regexp.MustCompile(`<sectionId>([\w\W]+?)</sectionId>`)
	var stopFlag = regexp.MustCompile(`<stopFlag>([\w\W]+?)</stopFlag>`)
	var trnstnid = regexp.MustCompile(`<trnstnid>([\w\W]+?)</trnstnid>`)
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

			thumb4 := fullSectDist.FindAllString(val, -1)
			if len(thumb4) == 0 {
				continue
			}

			thumb5 := gpsX.FindAllString(val, -1)
			if len(thumb5) == 0 {
				continue
			}

			thumb6 := gpsY.FindAllString(val, -1)
			if len(thumb6) == 0 {
				continue
			}

			thumb7 := isFullFlag.FindAllString(val, -1)
			if len(thumb7) == 0 {
				continue
			}

			thumb8 := islastyn.FindAllString(val, -1)
			if len(thumb8) == 0 {
				continue
			}

			thumb9 := isrunyn.FindAllString(val, -1)
			if len(thumb9) == 0 {
				continue
			}

			thumb10 := lastStTm.FindAllString(val, -1)
			if len(thumb10) == 0 {
				continue
			}

			thumb11 := lastStnId.FindAllString(val, -1)
			if len(thumb11) == 0 {
				continue
			}

			thumb12 := nextStId.FindAllString(val, -1)
			if len(thumb12) == 0 {
				continue
			}

			thumb13 := nextStTm.FindAllString(val, -1)
			if len(thumb13) == 0 {
				continue
			}

			thumb14 := plainNo.FindAllString(val, -1)
			if len(thumb14) == 0 {
				continue
			}

			thumb15 := posX.FindAllString(val, -1)
			if len(thumb15) == 0 {
				continue
			}

			thumb16 := posY.FindAllString(val, -1)
			if len(thumb16) == 0 {
				continue
			}

			thumb17 := rtDist.FindAllString(val, -1)
			if len(thumb17) == 0 {
				continue
			}

			thumb18 := sectDist.FindAllString(val, -1)
			if len(thumb18) == 0 {
				continue
			}

			thumb19 := sectOrd.FindAllString(val, -1)
			if len(thumb19) == 0 {
				continue
			}

			thumb20 := sectionId.FindAllString(val, -1)
			if len(thumb20) == 0 {
				continue
			}

			thumb21 := stopFlag.FindAllString(val, -1)
			if len(thumb21) == 0 {
				continue
			}

			thumb22 := trnstnid.FindAllString(val, -1)
			if len(thumb22) == 0 {
				continue
			}

			thumb23 := vehId.FindAllString(val, -1)
			if len(thumb23) == 0 {
				continue
			}

			mgetBusRouteListTagItem[Index] = getBusPosByRtidTag{thumb1[0], thumb2[0],
				thumb3[0], thumb4[0], thumb5[0], thumb6[0], thumb7[0],
				thumb8[0], thumb9[0], thumb10[0], thumb11[0], thumb12[0], thumb13[0],
				thumb14[0], thumb15[0], thumb16[0],
				thumb17[0], thumb18[0], thumb19[0], thumb20[0], thumb21[0], thumb22[0], thumb23[0],
			}
			Index++
		}
	}

	return true
}

func main() {

	getBusPosByRtid()
	for _, val := range mgetBusRouteListTagItem {

		sResult := fmt.Sprintf("차량유형:%s 혼잡도:%s 제공시간:%s 정류소간 거리:%s"+
			"맵매칭X좌표 (WGS84):%s 맵매칭Y좌표 (WGS84):%s 만차여부:%s"+
			"막차여부:%s 해당차량 운행여부:%s 종점도착소요시간:%s"+
			"최종정류장 ID:%s 다음 주요정류소ID:%s 다음 주요정류소 도착예정시간:%s"+
			"차량번호:%s 맵매칭X좌표 (GRS80):%s 맵매칭Y좌표 (GRS80):%s"+
			"노선옵셋거리 (Km):%s 정류장 구간이동거리:%s 구간순번:%s 구간ID:%s"+
			"정류소도착여부 (0:운행중, 1:도착):%s 회차지 정류소ID:%s 버스 ID:%s",
			findJobTag(val.busType),
			findJobTag(val.congetion),
			findJobTag(val.dataTm),
			findJobTag(val.fullSectDist),
			findJobTag(val.gpsX),
			findJobTag(val.gpsY),
			findJobTag(val.isFullFlag),
			findJobTag(val.islastyn),
			findJobTag(val.isrunyn),
			findJobTag(val.lastStTm),
			findJobTag(val.lastStnId),
			findJobTag(val.nextStId),
			findJobTag(val.nextStTm),
			findJobTag(val.plainNo),
			findJobTag(val.posX),
			findJobTag(val.posY),
			findJobTag(val.rtDist),
			findJobTag(val.sectDist),
			findJobTag(val.sectOrd),
			findJobTag(val.sectionId),
			findJobTag(val.stopFlag),
			findJobTag(val.trnstnid),
			findJobTag(val.vehId))
		fmt.Println(sResult)
	}
}
