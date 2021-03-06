package net.teamfruit.rerecipecreators;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.inventory.Recipe;

import guava10.com.google.common.collect.Sets;

public class RecipeRegistrar {
	private final Server server;
	private Set<Recipe> myrecipes = Sets.newHashSet();

	public RecipeRegistrar(final Server server) {
		this.server = server;
	}

	public void addRecipe(final Recipe recipe) {
		this.myrecipes.add(recipe);
		//if (!"".isEmpty())
		//	return;
		this.server.addRecipe(recipe);
	}

	public void setRecipes(final Collection<Recipe> recipes) {
		clearRecipe();
		addRecipes(recipes);
	}

	public void addRecipes(final Collection<Recipe> recipes) {
		for (final Recipe recipe : recipes)
			addRecipe(recipe);
	}

	public void removeRecipe(final Recipe recipe) {
		if (recipe==null)
			return;
		for (final Iterator<Recipe> itr = this.myrecipes.iterator(); itr.hasNext();) {
			final Recipe itrecipe = itr.next();
			if (Recipes.recipeEquals(recipe, itrecipe))
				itr.remove();
		}
		//if (!"".isEmpty())
		//	return;
		for (final Iterator<Recipe> itr = this.server.recipeIterator(); itr.hasNext();) {
			Recipe itrecipe = null;
			try {
				itrecipe = itr.next();
			} catch (final Throwable t) {
			}
			if (Recipes.ingredientEquals(recipe, itrecipe))
				itr.remove();
		}
	}

	public void clearRecipe() {
		if (!this.myrecipes.isEmpty()) {
			this.myrecipes.clear();
			//if (!"".isEmpty())
			//	return;
			for (final Iterator<Recipe> itr = this.server.recipeIterator(); itr.hasNext();) {
				Recipe itrecipe = null;
				try {
					itrecipe = itr.next();
				} catch (final Throwable t) {
				}
				for (final Recipe myrecipe : this.myrecipes)
					if (Recipes.ingredientEquals(myrecipe, itrecipe))
						itr.remove();
			}
		}
	}
}
