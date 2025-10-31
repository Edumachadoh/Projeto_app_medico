package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.ForumTopic

//Defines all data operations for ForumTopics
//Inherits all basic save(), delete(), find() functions from CrudRepository
interface ForumTopicRepository : CrudRepository<ForumTopic>
//No custom functions are needed for comments beyond standard CRUD