package br.uff.labtempo.osiris.schedule;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@Profile("function_schedule")
@EnableScheduling
public class FunctionComponentCreatorSchedule {
    public void createMaxFunction() {}
    public void createMinFunction() {}
    public void createAverageFunction() {}
    public void createSumFunction() {}
}
