// 노선의 지도상 경로를 리턴한다.
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

type getRoutePathTag struct {
	gpsX string //좌표X (WGS84)
	gpsY string //좌표Y (WGS84)
	no   string //순번
	posX string //좌표X (GRS80)
	posY string //좌표Y (GRS80)
}

var mRoutePathTagItem = make(map[int]getRoutePathTag)

func findJobTag(html string) string {
	first := strings.Index(html, ">")
	end := strings.LastIndex(html, "<")
	return string(html[first+1 : end])
}

func getRoutePath(sbusRouteId string) bool {

	defaulturl := "http://ws.bus.go.kr/api/rest/busRouteInfo/getRoutePath?"
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
	var gpsX = regexp.MustCompile(`<gpsX>([\w\W]+?)</gpsX>`)
	var gpsY = regexp.MustCompile(`<gpsY>([\w\W]+?)</gpsY>`)
	var no = regexp.MustCompile(`<no>([\w\W]+?)</no>`)
	var posX = regexp.MustCompile(`<posX>([\w\W]+?)</posX>`)
	var posY = regexp.MustCompile(`<posY>([\w\W]+?)</posY>`)
	var itemList = regexp.MustCompile(`<itemList>([\w\W]+?)</itemList>`)
	match := itemList.FindAllString(string(body), -1)
	if match != nil {

		for _, val := range match {

			thumb1 := gpsX.FindAllString(val, -1)
			if len(thumb1) == 0 {
				continue
			}

			thumb2 := gpsY.FindAllString(val, -1)
			if len(thumb2) == 0 {
				continue
			}

			thumb3 := no.FindAllString(val, -1)
			if len(thumb3) == 0 {
				continue
			}

			thumb4 := posX.FindAllString(val, -1)
			if len(thumb4) == 0 {
				continue
			}

			thumb5 := posY.FindAllString(val, -1)
			if len(thumb5) == 0 {
				continue
			}

			mRoutePathTagItem[Index] = getRoutePathTag{thumb1[0],
				thumb2[0], thumb3[0], thumb4[0], thumb5[0],
			}
			Index++
		}
	}

	return true
}

func main() {

	getRoutePath("117900003") //금천03
	for _, val := range mRoutePathTagItem {
		sResult := fmt.Sprintf("좌표X (WGS84):%s 좌표Y (WGS84):%s 순번:%s 좌표X (GRS80):%s 좌표Y (GRS80):%s \n", findJobTag(val.gpsX), findJobTag(val.gpsY), findJobTag(val.no), findJobTag(val.posX), findJobTag(val.posY))
		fmt.Println(sResult)
	}
}
