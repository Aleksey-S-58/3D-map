/**
 * This class should provide custom object type processing, 
 * fore example it could be: sound, may be video or some animations.
 * Please override method process = function (name, type, mapCanvas)
 * to provide additional functionality.
 * 
 * The algorithm of a historical soundtracks support:
 * collect all listened stories
 * collect stories available in the current location
 * display those which were not listened early
 * download and reproduce one by request
 */
function EffectListener () {
	this.storyDiv = "storyDiv";
	this.playedStoryList = new Array();
	this.publishedStoryList = new Array();
	this.storyType = "STORY";
	this.storyCounter = 0; // The number of shown stories.
	this.maxStoryCount = 3; // The maximum number of stories. 
}

/**
 * Override this function to provide support of your own custom object processors.
 * This method is called by MapCanvas during the processing 
 * of a list of object locations if object type is not 
 * either SPRITE or THREE_D_OBJECT
 * See MapCanvas.getObjectsListOnMap when it calls loadCustomObject(...)
 * 
 * MapCanvas.customObjectsOnScene - contains a list of custom objects which were already added to a scene,
 * incoming object will be added to the list after execution of this function.
 */
EffectListener.prototype.process = function (objectOnMap, mapCanvas) {
	// This method will print name of a story on a DIV element.
	if (objectOnMap.type == this.storyType) {
		if (this.storyCounter < this.maxStoryCount) {
			this.addStory(objectOnMap.name, objectOnMap.description, mapCanvas);
			this.storyCounter = this.storyCounter + 1;
		}
	}
}

/**
 * The method adds a label with a function associated with the name of a story. 
 */
EffectListener.prototype.addStory = function (name, description, mapCanvas) {
	customEffectListener = this;
	div = this.addDivIfAbsent(this.storyDiv);
	if (!this.isPlayed(name) && !this.isPublished(name)) {
		var label = document.createElement("label");
		label.setAttribute("id", this.getLabelName(name));
		label.innerHTML = "<br><image src='Story.png'/>" + description;
		label.addEventListener("click", function() {
			customEffectListener.loadStory(name, mapCanvas);
		}); // Add function.
		div.appendChild(label);
		this.publishedStoryList.push(name);
	}
}

EffectListener.prototype.getLabelName = function (name) {
	return "story_" + name;
}

EffectListener.prototype.isPlayed = function (name) {
	for (var i = 0; i < this.playedStoryList.length; i = i + 1) {
		if (this.playedStoryList[i] == name) {
			return true;
		}
	}
	return false;
}

EffectListener.prototype.isPublished = function (name) {
	for (var i = 0; i < this.publishedStoryList.length; i = i + 1) {
		if (this.publishedStoryList[i] == name) {
			return true;
		}
	}
	return false;
}

EffectListener.prototype.addDivIfAbsent = function (divName) {
	result = document.getElementById(divName);
	if (result == null) {
		div = document.createElement("div");
		div.setAttribute("class", "storydiv");
		div.setAttribute("id", divName);
		document.getElementById("div-canvas").appendChild(div);		
		return div;
	} else {
		return result;
	}
}

/**
 * This method is intended to remove an object of a custom type from a scene or from anywhere.
 * 
 * MapCanvas.customObjectsOnScene - contains a list of custom objects which exists on a scene,
 * after call of this function an object will be removed from the list.
 */
EffectListener.prototype.remove = function (name, type, latitude, longitude, hight, mapCanvas) {
	if (type == this.storyType) {
		if (this.isPublished(name)) {
			// remove label.
			element = document.getElementById(this.getLabelName(name));
			element.parentNode.removeChild(element);
			if (this.storyCounter > 0) {
				this.storyCounter = this.storyCounter - 1;			
			}
			// remove from a list of published.
			mapCanvas.removeByValue(this.publishedStoryList, name);
		}
	}
	// An object will be removed from MapCanvas.customObjectsOnScene after the method was called.
}

EffectListener.prototype.findNotPlayed = function(mapCanvas) {
	for (var i = 0; i < mapCanvas.customObjectsOnScene.length; i = i + 1) {
		if (mapCanvas.customObjectsOnScene[i].type = this.storyType
				&& !this.isPlayed(mapCanvas.customObjectsOnScene[i].name)
				&& !this.isPublished(mapCanvas.customObjectsOnScene[i].name)) {
			return mapCanvas.customObjectsOnScene[i];
		}
	}
	return null;
}

/**
 * This method downloads a story by its name from back-end and play it.
 * The method also should write down the name of the played track not to show it again.
 */
EffectListener.prototype.loadStory = function (name, mapCanvas) {
	// TODO load story from back-end then play it.
	this.loadAndPlay(name, mapCanvas); // Load story from back-end then play it.
	this.playedStoryList.push(name);
	var element = document.getElementById(this.getLabelName(name)); // remove label
	element.parentNode.removeChild(element);
	this.storyCounter = this.storyCounter - 1;
	// remove object played object mapCanvas.customObjectList
	var newObject = this.findNotPlayed(mapCanvas);// add some other not played object
	var newName = newObject.name;
	var newDescription = newObject.description;
	if (newName != null) {
		this.addStory(newName, newDescription, mapCanvas);
	}
	mapCanvas.removeByValue(this.publishedStoryList, name);// REMOVE BY VALUE
}

EffectListener.prototype.getStoryURL = function (name, mapCanvas) {
	return mapCanvas.SERVER_IP + "/map/story/" + name;
}

EffectListener.prototype.loadAndPlay = function (name, mapCanvas) {
	var storyUrl = this.getStoryURL(name, mapCanvas);
	var audio = document.createElement("audio");
	audio.style.display = "none";
	audio.src = storyUrl;
	audio.autoplay = true;
	audio.onended = function () {
		audio.remove();
	}
	document.body.appendChild(audio);
}
