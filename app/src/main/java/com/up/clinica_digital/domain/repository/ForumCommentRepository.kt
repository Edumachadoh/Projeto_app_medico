package com.up.clinica_digital.domain.repository

import com.up.clinica_digital.domain.model.ForumComment

//Defines all data operations for ForumComments
//Inherits all basic save(), delete(), find() functions from CrudRepository
interface ForumCommentRepository : CrudRepository<ForumComment>
//No custom functions are needed for comments beyond standard CRUD