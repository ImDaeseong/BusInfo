// getStaionByRoute
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

type getStaionByRouteTag struct {
	arsId        string //정류소 고유번호
	beginTm      string //첫차 시간
	busRouteId   string //노선 ID
	busRouteNm   string //노선명
	direction    string //진행방향
	gpsX         string //X좌표 (WGS 84)
	gpsY         string //Y좌표 (WGS 84)
	lastTm       string //막차 시간
	posX         string //좌표X (GRS80)
	posY         string //좌표Y (GRS80)
	routeType    string //노선 유형 (1:공항, 2:마을, 3:간선, 4:지선, 5:순환, 6:광역, 7:인천, 8:경기, 9:폐지, 0:공용)
	sectSpd      string //구간속도
	section      string //구간 ID
	seq          string //순번
	station      string //정류소 ID
	stationNm    string //정류소 이름
	stationNo    string //정류소 고유번호
	transYn      string //회차지 여부 (Y:회차, N:회차지아님)
	fullSectDist string //정류소간 거리
	trnstnid     string //회차지 정류소ID
}

var mStaionByRouteTagItem = make(map[int]getStaionByRouteTag)

func findJobTag(html string) string {
	first := strings.Index(html, ">")
	end := strings.LastIndex(html, "<")
	return string(html[first+1 : end])
}

func getStaionByRoute(sbusRouteId string) bool {

	defaulturl := "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute?"
	busRouteId := fmt.Sprintf("busRouteId=%s", sbusRouteId)
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
	var arsId = regexp.MustCompile(`<arsId>([\w\W]+?)</arsId>`)
	var beginTm = regexp.MustCompile(`<beginTm>([\w\W]+?)</beginTm>`)
	var busRouteId2 = regexp.MustCompile(`<busRouteId>([\w\W]+?)</busRouteId>`)
	var busRouteNm = regexp.MustCompile(`<busRouteNm>([\w\W]+?)</busRouteNm>`)
	var direction = regexp.MustCompile(`<direction>([\w\W]+?)</direction>`)
	var gpsX = regexp.MustCompile(`<gpsX>([\w\W]+?)</gpsX>`)
	var gpsY = regexp.MustCompile(`<gpsY>([\w\W]+?)</gpsY>`)
	var lastTm = regexp.MustCompile(`<lastTm>([\w\W]+?)</lastTm>`)
	var posX = regexp.MustCompile(`<posX>([\w\W]+?)</posX>`)
	var posY = regexp.MustCompile(`<posY>([\w\W]+?)</posY>`)
	var routeType = regexp.MustCompile(`<routeType>([\w\W]+?)</routeType>`)
	var sectSpd = regexp.MustCompile(`<sectSpd>([\w\W]+?)</sectSpd>`)
	var section = regexp.MustCompile(`<section>([\w\W]+?)</section>`)
	var seq = regexp.MustCompile(`<seq>([\w\W]+?)</seq>`)
	var station = regexp.MustCompile(`<station>([\w\W]+?)</station>`)
	var stationNm = regexp.MustCompile(`<stationNm>([\w\W]+?)</stationNm>`)
	var stationNo = regexp.MustCompile(`<stationNo>([\w\W]+?)</stationNo>`)
	var transYn = regexp.MustCompile(`<transYn>([\w\W]+?)</transYn>`)
	var fullSectDist = regexp.MustCompile(`<fullSectDist>([\w\W]+?)</fullSectDist>`)
	var trnstnid = regexp.MustCompile(`<trnstnid>([\w\W]+?)</trnstnid>`)
	var itemList = regexp.MustCompile(`<itemList>([\w\W]+?)</itemList>`)
	match := itemList.FindAllString(string(body), -1)
	if match != nil {

		for _, val := range match {

			thumb1 := arsId.FindAllString(val, -1)
			if len(thumb1) == 0 {
				continue
			}

			thumb2 := beginTm.FindAllString(val, -1)
			if len(thumb2) == 0 {
				continue
			}

			thumb3 := busRouteId2.FindAllString(val, -1)
			if len(thumb3) == 0 {
				continue
			}

			thumb4 := busRouteNm.FindAllString(val, -1)
			if len(thumb4) == 0 {
				continue
			}

			thumb5 := direction.FindAllString(val, -1)
			if len(thumb5) == 0 {
				continue
			}

			thumb6 := gpsX.FindAllString(val, -1)
			if len(thumb6) == 0 {
				continue
			}

			thumb7 := gpsY.FindAllString(val, -1)
			if len(thumb7) == 0 {
				continue
			}

			thumb8 := lastTm.FindAllString(val, -1)
			if len(thumb8) == 0 {
				continue
			}

			thumb9 := posX.FindAllString(val, -1)
			if len(thumb9) == 0 {
				continue
			}

			thumb10 := posY.FindAllString(val, -1)
			if len(thumb10) == 0 {
				continue
			}

			thumb11 := routeType.FindAllString(val, -1)
			if len(thumb11) == 0 {
				continue
			}

			thumb12 := sectSpd.FindAllString(val, -1)
			if len(thumb12) == 0 {
				continue
			}

			thumb13 := section.FindAllString(val, -1)
			if len(thumb13) == 0 {
				continue
			}

			thumb14 := seq.FindAllString(val, -1)
			if len(thumb14) == 0 {
				continue
			}

			thumb15 := station.FindAllString(val, -1)
			if len(thumb15) == 0 {
				continue
			}

			thumb16 := stationNm.FindAllString(val, -1)
			if len(thumb16) == 0 {
				continue
			}

			thumb17 := stationNo.FindAllString(val, -1)
			if len(thumb17) == 0 {
				continue
			}

			thumb18 := transYn.FindAllString(val, -1)
			if len(thumb18) == 0 {
				continue
			}

			thumb19 := fullSectDist.FindAllString(val, -1)
			if len(thumb19) == 0 {
				continue
			}

			thumb20 := trnstnid.FindAllString(val, -1)
			if len(thumb20) == 0 {
				continue
			}

			mStaionByRouteTagItem[Index] = getStaionByRouteTag{thumb1[0], thumb2[0],
				thumb3[0], thumb4[0], thumb5[0], thumb6[0], thumb7[0], thumb8[0],
				thumb9[0], thumb10[0], thumb11[0], thumb12[0], thumb13[0], thumb14[0],
				thumb15[0], thumb16[0], thumb17[0], thumb18[0], thumb19[0], thumb20[0],
			}
			Index++
		}
	}

	return true
}

func main() {

	getStaionByRoute("117900003") //금천03
	for _, val := range mStaionByRouteTagItem {
		sResult := fmt.Sprintf("정류소 고유번호:%s 첫차 시간:%s 노선 ID:%s 노선명:%s 진행방향:%s X좌표 (WGS 84):%s Y좌표 (WGS 84):%s 막차 시간:%s 좌표X (GRS80):%s 좌표Y (GRS80):%s 노선 유형:%s 구간속도:%s 구간 ID:%s 순번:%s 정류소 ID:%s 정류소 이름:%s 정류소 고유번호:%s 회차지 여부:%s 정류소간 거리:%s 회차지 정류소ID:%s",
			findJobTag(val.arsId), findJobTag(val.beginTm), findJobTag(val.busRouteId), findJobTag(val.busRouteNm), findJobTag(val.direction),
			findJobTag(val.gpsX), findJobTag(val.gpsY), findJobTag(val.lastTm), findJobTag(val.posX), findJobTag(val.posY),
			findJobTag(val.routeType), findJobTag(val.sectSpd), findJobTag(val.section), findJobTag(val.seq), findJobTag(val.station),
			findJobTag(val.stationNm), findJobTag(val.stationNo), findJobTag(val.transYn), findJobTag(val.fullSectDist), findJobTag(val.trnstnid))
		fmt.Println(sResult)
	}
}
