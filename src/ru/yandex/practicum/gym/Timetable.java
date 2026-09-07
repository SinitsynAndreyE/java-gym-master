package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, List<TrainingSession>> daySessions;
        List<TrainingSession> timeSessions;

        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            daySessions = timetable.get(trainingSession.getDayOfWeek());
            if (daySessions.containsKey(trainingSession.getTimeOfDay())) {
                timeSessions = daySessions.get(trainingSession.getTimeOfDay());
                timeSessions.add(trainingSession);
            } else {
                timeSessions = new ArrayList<>(List.of(trainingSession));
            }

        } else {
            daySessions = new TreeMap<>();
            timeSessions = new ArrayList<>(List.of(trainingSession));
        }
        daySessions.put(trainingSession.getTimeOfDay(), timeSessions);
        timetable.put(trainingSession.getDayOfWeek(),daySessions);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek).get(timeOfDay);
        } else {
            return Collections.emptyList();
        }
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        List<CounterOfTrainings> countersOfTrainings = new ArrayList<>();
        Map<Coach, Integer> trainingCounter = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySessions : timetable.values()) {
            for (List<TrainingSession> timeSessions : daySessions.values()) {
                for (TrainingSession session : timeSessions) {
                    int counter = trainingCounter.getOrDefault(session.getCoach(),0);
                    counter++;
                    trainingCounter.put(session.getCoach(), counter);
                }
            }
        }

        for (Map.Entry<Coach, Integer> entry : trainingCounter.entrySet()) {
            CounterOfTrainings coachCounter = new CounterOfTrainings(entry.getKey(), entry.getValue());
            countersOfTrainings.add(coachCounter);
        }
        countersOfTrainings.sort(Comparator.reverseOrder());
        return countersOfTrainings;
    }
}
