/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.controllers;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

import app.connectors.ConnectionUtils;

import app.data.jpa.domain.Observation;
import app.data.jpa.domain.Room;
import app.data.jpa.domain.Event;
import app.data.jpa.domain.EventTop;

import app.data.jpa.repository.ObservationRepository;
import app.data.jpa.repository.RoomRepository;
import app.data.jpa.repository.EventRepository;
import app.services.cmx.CmxNotificationSubscriptionAPI;
import app.utils.GetProperties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController

public class CMXController {
    private final   GetProperties   properties      = new GetProperties("PROPERTIES");
    private final   Logger          log             = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private EventRepository eventRepository;

   
    public CMXController(){
        log.debug("IN AuthController");
        log.debug("OUT OK AuthController");
    }

 
    
	@RequestMapping(method = RequestMethod.POST, value = "/observation", produces = { "application/json",
			"text/json" })
	public void createObservation(@RequestBody String observation, HttpServletResponse response) {
		log.info("createObservation() | START |");
        try {

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(observation);
            String macadr = (String) jsonObject.get("clientMacAddress");
            String x = (String) jsonObject.get("clientX");
            String y = (String) jsonObject.get("clientY");
            String unc = (String) jsonObject.get("clientUncert");
            String ts = (String) jsonObject.get("clientTimeStamp");
            String os = (String) jsonObject.get("clientOs");
            String apmac = (String) jsonObject.get("apMacAddress");

		
			Observation obs = new Observation( macadr, Double.parseDouble(x), Double.parseDouble(y), unc, this.convertStringToLocalDateTime(ts), os, apmac) ;
            this.observationRepository.save(obs);
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		log.info("createObservation() | END | ");
	}

    @RequestMapping(method = RequestMethod.POST, value = "/room", produces = { "application/json",
            "text/json" })
    public void createRoom(@RequestBody String roomIn, HttpServletResponse response) {
        log.info("createRoom() | START |");
        try {

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(roomIn);
            String name = (String) jsonObject.get("name");
            String xMin = (String) jsonObject.get("xMin");
            String yMin = (String) jsonObject.get("yMin");
            String xMax = (String) jsonObject.get("xMax");
            String yMax = (String) jsonObject.get("yMax");
            String sparkRoomId = (String) jsonObject.get("sparkRoomId");
        
            Room room = new Room( name, Double.parseDouble(xMin), Double.parseDouble(yMin), Double.parseDouble(xMax), Double.parseDouble(yMax), sparkRoomId);
            this.roomRepository.save(room);
        
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("createRoom() | END | ");
    }

     @RequestMapping(method = RequestMethod.POST, value = "/event", produces = { "application/json",
            "text/json" })
    public void createEvent(@RequestBody String eventIn, HttpServletResponse response) {
        log.info("createEvent() | START |");
        try {

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(eventIn);
            String name = (String) jsonObject.get("name");
            String speaker = (String) jsonObject.get("speaker");
            String speakerImgUrl = (String) jsonObject.get("speakerImgUrl");
            if(speakerImgUrl==null){
                speakerImgUrl="https://cdn.elderlawanswers.com/common/uploads/photos/default-avatar.jpg";
            }
            String startDate = (String) jsonObject.get("startDate");
            String endDate = (String) jsonObject.get("endDate");
            String tags = (String) jsonObject.get("tags");
            String roomId = (String) jsonObject.get("roomId");

        
            Event event = new Event( name, speaker, 
                this.convertStringToLocalDateTime(startDate),
                this.convertStringToLocalDateTime(endDate), tags, Long.parseLong(roomId), speakerImgUrl) ;
            this.eventRepository.save(event);
        
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("createEvent() | END | ");
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/observations", produces = { "application/json", "text/json" })
	public List<Observation> getAllObservations() {
		log.info("getAllObservations() | START |");
		List<Observation> result = null;
		try {
		  result = this.observationRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		log.info("getAllObservations() | END ");
		return result;
	}

    @RequestMapping(method = RequestMethod.GET, path = "/events", produces = { "application/json", "text/json" })
    public List<Event> getAllEvents() {
        log.info("getAllEvents() | START |");
        List<Event> result = null;
        try {
          result = this.eventRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("getAllEvents() | END ");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/rooms", produces = { "application/json", "text/json" })
    public List<Room> getAllRooms() {
        log.info("getAllRooms() | START |");
        List<Room> result = null;
        try {
          result = this.roomRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("getAllRooms() | END ");
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/events/top3", produces = { "application/json", "text/json" })
    public List<EventTop> getTop3Events() {
        log.info("getTop3Events() | START |");
        try {
            List<EventTop>        result        = null;
            List<Event>           events        = this.eventRepository.findAll();

            for(Event event: events){
                Room              room          = this.roomRepository.findById(event.getRoomId());
                List<Observation> observations  = this.observationRepository.findDistinctObservationsByTimestampBetweenAndXGreaterThan(event.getStartDate(), 
                                                    event.getEndDate(), room.getX1Min());
                result.add(new EventTop(event, observations.size()));
            }
            log.info("getTop3Events() | END ");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }

    private static LocalDateTime convertStringToLocalDateTime(String date) {
        date = date.substring(0,10)+ " " + date.substring(11,19);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
	
	
}
