//노선정보조회 서비스

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

type getBusRouteListTag struct {
	busRouteId  string //노선 ID
	busRouteNm  string //노선명
	corpNm      string //운수사명
	edStationNm string //종점
	firstBusTm  string //금일첫차시간
	firstLowTm  string //금일저상첫차시간
	lastBusTm   string //금일막차시간
	lastBusYn   string //막차운행여부
	lastLowTm   string //금일저상막차시간
	length      string //노선 길이 (Km)
	routeType   string //노선 유형 (1:공항, 2:마을, 3:간선, 4:지선, 5:순환, 6:광역, 7:인천, 8:경기, 9:폐지, 0:공용)
	stStationNm string //기점
	term        string //배차간격 (분)
}

var mgetBusRouteListTagItem = make(map[int]getBusRouteListTag)

func findJobTag(html string) string {
	first := strings.Index(html, ">")
	end := strings.LastIndex(html, "<")
	return string(html[first+1 : end])
}

func getBusRouteList() bool {

	defaulturl := "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?"
	busRouteId := fmt.Sprintf("strSrch=%d", 3) //3번버스
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
	var busRouteId1 = regexp.MustCompile(`<busRouteId>([\w\W]+?)</busRouteId>`)
	var busRouteNm = regexp.MustCompile(`<busRouteNm>([\w\W]+?)</busRouteNm>`)
	var corpNm = regexp.MustCompile(`<corpNm>([\w\W]+?)</corpNm>`)
	var edStationNm = regexp.MustCompile(`<edStationNm>([\w\W]+?)</edStationNm>`)
	var firstBusTm = regexp.MustCompile(`<firstBusTm>([\w\W]+?)</firstBusTm>`)
	var firstLowTm = regexp.MustCompile(`<firstLowTm>([\w\W]+?)</firstLowTm>`)
	var lastBusTm = regexp.MustCompile(`<lastBusTm>([\w\W]+?)</lastBusTm>`)
	var lastBusYn = regexp.MustCompile(`<lastBusYn>([\w\W]+?)</lastBusYn>`)
	var lastLowTm = regexp.MustCompile(`<lastLowTm>([\w\W]+?)</lastLowTm>`)
	var length = regexp.MustCompile(`<length>([\w\W]+?)</length>`)
	var routeType = regexp.MustCompile(`<routeType>([\w\W]+?)</routeType>`)
	var stStationNm = regexp.MustCompile(`<stStationNm>([\w\W]+?)</stStationNm>`)
	var term = regexp.MustCompile(`<term>([\w\W]+?)</term>`)
	var itemList = regexp.MustCompile(`<itemList>([\w\W]+?)</itemList>`)
	match := itemList.FindAllString(string(body), -1)
	if match != nil {

		for _, val := range match {

			thumb1 := busRouteId1.FindAllString(val, -1)
			if len(thumb1) == 0 {
				continue
			}

			thumb2 := busRouteNm.FindAllString(val, -1)
			if len(thumb2) == 0 {
				continue
			}

			thumb3 := corpNm.FindAllString(val, -1)
			if len(thumb3) == 0 {
				continue
			}

			thumb4 := edStationNm.FindAllString(val, -1)
			if len(thumb4) == 0 {
				continue
			}

			thumb5 := firstBusTm.FindAllString(val, -1)
			if len(thumb5) == 0 {
				continue
			}

			thumb6 := firstLowTm.FindAllString(val, -1)
			if len(thumb6) == 0 {
				continue
			}

			thumb7 := lastBusTm.FindAllString(val, -1)
			if len(thumb7) == 0 {
				continue
			}

			thumb8 := lastBusYn.FindAllString(val, -1)
			if len(thumb8) == 0 {
				continue
			}

			thumb9 := lastLowTm.FindAllString(val, -1)
			if len(thumb9) == 0 {
				continue
			}

			thumb10 := length.FindAllString(val, -1)
			if len(thumb10) == 0 {
				continue
			}

			thumb11 := routeType.FindAllString(val, -1)
			if len(thumb11) == 0 {
				continue
			}

			thumb12 := stStationNm.FindAllString(val, -1)
			if len(thumb12) == 0 {
				continue
			}

			thumb13 := term.FindAllString(val, -1)
			if len(thumb13) == 0 {
				continue
			}

			mgetBusRouteListTagItem[Index] = getBusRouteListTag{thumb1[0], thumb2[0],
				thumb3[0], thumb4[0], thumb5[0], thumb6[0], thumb7[0],
				thumb8[0], thumb9[0], thumb10[0], thumb11[0], thumb12[0], thumb13[0],
			}
			Index++
		}
	}

	return true
}

func main() {

	getBusRouteList()
	for _, val := range mgetBusRouteListTagItem {

		if strings.Contains(val.busRouteNm, "금천03") || strings.Contains(val.busRouteNm, "1500고양") || strings.Contains(val.busRouteNm, "9707") || strings.Contains(val.busRouteNm, "830파주") {

			sResult := fmt.Sprintf("노선 ID:%s\n 노선명:%s\n 운수사명:%s\n 종점:%s\n 금일첫차시간:%s\n 금일저상첫차시간:%s\n 금일막차시간:%s\n 막차운행여부:%s\n 금일저상막차시간:%s\n 노선 길이 (Km):%s\n 노선 유형:%s\n 기점:%s\n 배차간격 (분):%s",
				findJobTag(val.busRouteId), findJobTag(val.busRouteNm), findJobTag(val.corpNm), findJobTag(val.edStationNm), findJobTag(val.firstBusTm),
				findJobTag(val.firstLowTm), findJobTag(val.lastBusTm), findJobTag(val.lastBusYn), findJobTag(val.lastLowTm), findJobTag(val.length),
				findJobTag(val.routeType), findJobTag(val.stStationNm), findJobTag(val.term))
			fmt.Println(sResult)
			break
		}

	}

}
