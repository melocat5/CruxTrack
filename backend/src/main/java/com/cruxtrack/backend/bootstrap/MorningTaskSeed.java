package com.cruxtrack.backend.bootstrap;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cruxtrack.backend.morning.MorningTask;
import com.cruxtrack.backend.morning.MorningTaskRepository;

@Component
@Order(2)
public class MorningTaskSeed implements ApplicationRunner {

	private final MorningTaskRepository tasks;

	public MorningTaskSeed(MorningTaskRepository tasks) {
		this.tasks = tasks;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (tasks.count() > 0) {
			return;
		}
		tasks.save(new MorningTask("Unlock all entrances", true));
		tasks.save(new MorningTask("Check safety equipment", true));
		tasks.save(new MorningTask("Review opening checklist", true));
	}
}
